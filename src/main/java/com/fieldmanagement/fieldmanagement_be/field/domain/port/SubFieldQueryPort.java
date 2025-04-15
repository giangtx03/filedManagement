package com.fieldmanagement.fieldmanagement_be.field.domain.port;

import com.fieldmanagement.fieldmanagement_be.field.domain.model.SubField;

import java.util.Optional;

public interface SubFieldQueryPort {
    Optional<SubField> findById(String id);
}
