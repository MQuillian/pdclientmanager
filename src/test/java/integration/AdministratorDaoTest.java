package integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pdclientmanager.model.projection.OfficeStatsDto;
import com.pdclientmanager.repository.AdministratorDao;

public class AdministratorDaoTest extends AbstractBaseTest{
        
        @Autowired
        AdministratorDao dao;
        
        @Test
        public void getOfficeStats_ShouldReturnPopulatedOfficeStatsDto() throws Exception {
            OfficeStatsDto dto = dao.getOfficeStats();
    }

}
