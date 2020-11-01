package com.pdclientmanager.util.mapper;

import com.pdclientmanager.model.form.JudgeForm;
import com.pdclientmanager.repository.entity.Judge;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-10-19T17:18:23-0400",
    comments = "version: 1.3.0.Final, compiler: Eclipse JDT (IDE) 3.16.0.v20181130-1748, environment: Java 11.0.1 (Oracle Corporation)"
)
@Component
public class JudgeMapperImpl implements JudgeMapper {

    @Override
    public Judge toJudge(JudgeForm dto) {
        if ( dto == null ) {
            return null;
        }

        Judge judge = new Judge();

        judge.setId( dto.getId() );
        judge.setName( dto.getName() );
        judge.setWorkingStatus( dto.getWorkingStatus() );

        return judge;
    }

    @Override
    public JudgeForm toJudgeForm(Judge entity) {
        if ( entity == null ) {
            return null;
        }

        JudgeForm judgeForm = new JudgeForm();

        judgeForm.setId( entity.getId() );
        judgeForm.setName( entity.getName() );
        judgeForm.setWorkingStatus( entity.getWorkingStatus() );

        return judgeForm;
    }
}
