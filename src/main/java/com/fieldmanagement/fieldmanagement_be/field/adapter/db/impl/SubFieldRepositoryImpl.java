package com.fieldmanagement.fieldmanagement_be.field.adapter.db.impl;

import com.fieldmanagement.fieldmanagement_be.field.adapter.db.JpaSubFieldRepository;
import com.fieldmanagement.fieldmanagement_be.field.adapter.mapper.SubFieldMapper;
import com.fieldmanagement.fieldmanagement_be.field.domain.model.SubField;
import com.fieldmanagement.fieldmanagement_be.field.domain.port.SubFieldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Primary
@RequiredArgsConstructor
@Repository("jpaSubFieldImpl")
public class SubFieldRepositoryImpl implements SubFieldRepository {
    private final JpaSubFieldRepository jpaSubFieldRepository;
    private final SubFieldMapper subFieldMapper;

    @Override
    public Optional<SubField> findById(String id) {
        return jpaSubFieldRepository.findById(id)
                .map(subFieldMapper::toSubField);
    }
}
