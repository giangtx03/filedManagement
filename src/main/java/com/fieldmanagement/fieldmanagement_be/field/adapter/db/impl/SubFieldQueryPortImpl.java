package com.fieldmanagement.fieldmanagement_be.field.adapter.db.impl;

import com.fieldmanagement.fieldmanagement_be.field.adapter.db.JpaSubFieldRepository;
import com.fieldmanagement.fieldmanagement_be.field.adapter.mapper.SubFieldMapper;
import com.fieldmanagement.fieldmanagement_be.field.domain.model.SubField;
import com.fieldmanagement.fieldmanagement_be.field.domain.port.SubFieldQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Primary
public class SubFieldQueryPortImpl implements SubFieldQueryPort {
    private final JpaSubFieldRepository jpaSubFieldRepository;
    private final SubFieldMapper subFieldMapper;

    @Override
    public Optional<SubField> findById(String id) {
        return jpaSubFieldRepository.findById(id)
                .map(subFieldMapper::toSubField);
    }
}
