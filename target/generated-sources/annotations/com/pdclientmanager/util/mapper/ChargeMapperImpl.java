package com.pdclientmanager.util.mapper;

import com.pdclientmanager.model.form.ChargeForm;
import com.pdclientmanager.repository.entity.Charge;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-10-19T17:18:15-0400",
    comments = "version: 1.3.0.Final, compiler: Eclipse JDT (IDE) 3.16.0.v20181130-1748, environment: Java 11.0.1 (Oracle Corporation)"
)
@Component
public class ChargeMapperImpl implements ChargeMapper {

    @Override
    public Charge toCharge(ChargeForm form) {
        if ( form == null ) {
            return null;
        }

        Charge charge = new Charge();

        charge.setId( form.getId() );
        charge.setName( form.getName() );
        charge.setStatute( form.getStatute() );

        return charge;
    }

    @Override
    public ChargeForm toChargeForm(Charge entity) {
        if ( entity == null ) {
            return null;
        }

        ChargeForm chargeForm = new ChargeForm();

        chargeForm.setId( entity.getId() );
        chargeForm.setName( entity.getName() );
        chargeForm.setStatute( entity.getStatute() );

        return chargeForm;
    }
}
