package com.liqourlocator.Liquor.Locator.repository;

import com.liqourlocator.Liquor.Locator.model.Establishment;
import com.liqourlocator.Liquor.Locator.model.EstablishmentType;
import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.repository.CouchbasePagingAndSortingRepository;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import java.util.List;

public interface EstablishmentTypeRepository extends CouchbaseRepository<EstablishmentType, String>
{
    @Query("SELECT license_type, META(b).id as _ID, META(b).cas as _CAS FROM #{#n1ql.bucket} as b UNNEST b.license_types as license_type WHERE b.type='license_types'")
    List<EstablishmentType> findAllEstablishmentTypes();

}
