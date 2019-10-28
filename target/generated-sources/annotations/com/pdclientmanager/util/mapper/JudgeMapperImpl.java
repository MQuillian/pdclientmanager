package com.pdclientmanager.util.mapper;

import com.pdclientmanager.model.dto.JudgeDto;
import com.pdclientmanager.model.entity.Judge;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-10-27T23:37:13-0400",
    comments = "version: 1.3.0.Final, compiler: Eclipse JDT (IDE) 3.16.0.v20181130-1748, environment: Java 11.0.1 (Oracle Corporation)"
)
*/
@Component
public class JudgeMapperImpl implements JudgeMapper {

    @Override
    public Judge toJudge(JudgeDto dto) {
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
    public JudgeDto toJudgeDto(Judge entity) {
        if ( entity == null ) {
            return null;
        }

        JudgeDto judgeDto = new JudgeDto();

        judgeDto.setId( entity.getId() );
        judgeDto.setName( entity.getName() );
        judgeDto.setWorkingStatus( entity.getWorkingStatus() );

        return judgeDto;
    }

    @Override
    public List<Judge> toJudgeList(List<JudgeDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Judge> list = new ArrayList<Judge>( dtos.size() );
        for ( JudgeDto judgeDto : dtos ) {
            list.add( toJudge( judgeDto ) );
        }

        return list;
    }

    @Override
    public List<JudgeDto> toJudgeDtoList(List<Judge> entities) {
        if ( entities == null ) {
            return null;
        }

        List<JudgeDto> list = new ArrayList<JudgeDto>( entities.size() );
        for ( Judge judge : entities ) {
            list.add( toJudgeDto( judge ) );
        }

        return list;
    }
}
