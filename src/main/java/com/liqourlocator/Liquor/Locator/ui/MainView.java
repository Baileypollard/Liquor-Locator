package com.liqourlocator.Liquor.Locator.ui;

import com.couchbase.client.java.document.json.JsonObject;
import com.liqourlocator.Liquor.Locator.model.Establishment;
import com.liqourlocator.Liquor.Locator.model.EstablishmentType;
import com.liqourlocator.Liquor.Locator.repository.EstablishmentRepository;
import com.liqourlocator.Liquor.Locator.repository.EstablishmentTypeRepository;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.vaadin.addons.searchbox.SearchBox;
import org.vaadin.alump.labelbutton.LabelButton;
import org.vaadin.alump.labelbutton.LabelButtonStyles;

import javax.servlet.annotation.WebServlet;
import javax.xml.ws.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Theme("valo")
@Widgetset(value = "com.vaadin.tapio.googlemaps.demo.DemoWidgetset")
@SpringUI(path = "/")
public class MainView extends UI
{
    @Autowired
    private EstablishmentRepository establishmentRepository;

    @Autowired
    private EstablishmentTypeRepository establishmentTypeRepository;

    private ComboBox<EstablishmentType> typesComboBox;
    private String apiKey = "AIzaSyA0HS0GBYbxEbdXhzsP7sUnk2MDT7j3XFw";
    private List<Establishment> establishments;
    private VerticalLayout panelLayout;
    private GoogleMap googleMap;

    @Override
    protected void init(VaadinRequest vaadinRequest)
    {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();

        SearchBox searchBox = new SearchBox("Search", SearchBox.ButtonPosition.LEFT);
        searchBox.addSearchListener(searchEvent ->
                {
                    if (typesComboBox.isEmpty())
                    {
                        establishments = establishmentRepository.findEstablishmentsByTown(searchEvent.getSearchTerm());
                    }
                    else
                    {
                        establishments = establishmentRepository.findEstablishmentsByTownAndType(searchEvent.getSearchTerm(),
                                typesComboBox.getSelectedItem().get().getType());
                    }
                    displayEstablishments(establishments);
                }
        );
        HorizontalLayout mapLayout = new HorizontalLayout();
        mapLayout.setSizeFull();

        googleMap = new GoogleMap(apiKey, null, "english");
        googleMap.setSizeFull();
        googleMap.setZoom(7);
        googleMap.setCenter(new LatLon(44.6488, -63.5752));

        panelLayout = new VerticalLayout();

        Panel closeEstablishmentsPanel = new Panel("Found Establishments");
        closeEstablishmentsPanel.setStyleName(ValoTheme.PANEL_WELL);
        closeEstablishmentsPanel.setContent(panelLayout);
        closeEstablishmentsPanel.setSizeFull();

        mapLayout.addComponents(googleMap, closeEstablishmentsPanel);
        mapLayout.setExpandRatio(googleMap, 1.0f);
        mapLayout.setExpandRatio(closeEstablishmentsPanel, 0.2f);

        typesComboBox = new ComboBox<>("License Type: ");
        List<EstablishmentType> types = establishmentTypeRepository.findAllEstablishmentTypes();
        typesComboBox.setItems(types);

        mainLayout.addComponents(searchBox, typesComboBox, mapLayout);
        mainLayout.setComponentAlignment(searchBox, Alignment.TOP_LEFT);

        mainLayout.setExpandRatio(mapLayout, 1.0f);

        setContent(mainLayout);
    }


    private void displayEstablishments(List<Establishment> establishments)
    {
        panelLayout.removeAllComponents();
        googleMap.clearMarkers();

        if (establishments.size() == 0)
        {
            Label noneFound = new Label("No Establishments Found");
            panelLayout.addComponent(noneFound);
            return;
        }

        //latlng could be null, so cycle through and find the first that isn't null and set the camera to it
        for (int i = 0; i < establishments.size(); i++)
        {
            if (establishments.get(i).getLatLong() != null)
            {
                googleMap.setCenter(establishments.get(i).getLatLong());
                googleMap.setZoom(15);
                break;
            }
        }

        establishments.forEach(establishment ->
        {
            Button button = new Button(establishment.getEstablishment());
            button.setStyleName(ValoTheme.BUTTON_BORDERLESS);
            button.addClickListener(clickEvent ->
            {
                Establishment clickedEstablishment = findClickedEstablishment(establishments, clickEvent.getButton().getCaption());
                if (clickedEstablishment != null)
                {
                    Establishment newEstablishment = getEstablishmentInformation(clickedEstablishment);

                    HorizontalLayout layout = createWindowUI(newEstablishment);

                    CreateWindowWithLayout window = new CreateWindowWithLayout(layout);
                    window.setDraggable(true);

                    getUI().getWindows().forEach(Window::close); //Close all current windows to prevent stacking

                    getUI().addWindow(window);
                }
                else
                {
                    Notification.show("Could not find the selected establishment", Notification.Type.WARNING_MESSAGE);
                }
            });

            panelLayout.addComponent(button);

            LatLon establishmentLocation = establishment.getLatLong();
            if (establishmentLocation != null)
            {
                googleMap.addMarker(establishment.getEstablishment(), establishment.getLatLong(), false, null);
            }
        });
    }

    private HorizontalLayout createWindowUI(Establishment establishment)
    {
        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setSizeFull();

        VerticalLayout leftLayout = new VerticalLayout();

        Label establishmentNameLabel = new Label(establishment.getEstablishment());
        establishmentNameLabel.setStyleName(ValoTheme.LABEL_BOLD);

        Label establishmentAddress = new Label("Address: " + establishment.getStreetAddress());
        Label establishmentCity = new Label("City/Town: " + establishment.getCityTown());
        Label establishmentProvince = new Label("Province: " + establishment.getProvince());
        Label establishmentRating = new Label("Average Rating: " + establishment.getRating());
        Label phoneNumber = new Label("Phone Number: " + establishment.getPhoneNumber());
        Label establishmentsNearBy = new Label("Total Establishments Nearby: ");
        Label otherNearBy = new Label("Other Bars Nearby: ");

        leftLayout.addComponents(establishmentNameLabel, establishmentAddress, establishmentCity, establishmentProvince, establishmentRating
        , phoneNumber, establishmentsNearBy, otherNearBy);

        VerticalLayout rightLayout = new VerticalLayout();

        GoogleMap map = new GoogleMap(apiKey, null, "english");
        map.setWidth("500px");
        map.setHeight("500px");
        map.addMarker(establishment.getEstablishment(), establishment.getLatLong(), false, null);
        map.setCenter(establishment.getLatLong());
        map.setZoom(15);

        rightLayout.addComponent(map);

        mainLayout.addComponents(leftLayout, rightLayout);

        return mainLayout;
    }

    private Establishment findClickedEstablishment(final List<Establishment> list, final String clickedEstablishment)
    {
        for (int i = 0; i < list.size(); i++)
        {
            if (list.get(i).getEstablishment().equals(clickedEstablishment))
            {
                return list.get(i);
            }
        }
        return null;
    }

    private Establishment getEstablishmentInformation(Establishment establishment)
    {
        String url = "https://api.yelp.com/v3/businesses/search";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_FORM_URLENCODED));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        headers.setBearerAuth("cdsBCLbuVL_cTXRcQqMGJV3BLJNP2pWpNme0l_BBvk02UHB-48fehnCuX5BxCh3Z-9CCbFIpZOTydzurdvDYvTiRDep0kkyXdDGjRv_ZrSQKkBtbCTkJ5PJ2TP-QXHYx");

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("term", establishment.getEstablishment())
                .queryParam("location", establishment.getCityTown())
                .queryParam("limit", 1);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        System.out.println("URL: " + builder.toUriString());

        ResponseEntity response = restTemplate.exchange(builder.build().toString(), HttpMethod.GET, entity, String.class);
        JsonObject json = JsonObject.fromJson(response.getBody().toString());
        JsonObject business = (JsonObject) json.getArray("businesses").get(0);

        String phoneNumber = business.getString("phone");
        String id = business.getString("id");
        String rating = business.getDouble("rating").toString();

        establishment.setRating(rating);
        establishment.setPhoneNumber(phoneNumber);
        establishment.setId(id);
        return establishment;
    }


    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MainView.class, productionMode = false, widgetset = "com.vaadin.tapio.googlemaps.demo.DemoWidgetset")
    public static class MyUIServlet extends VaadinServlet
    {

    }
}