package integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.pdclientmanager.repository.AttorneyRepository;
import com.pdclientmanager.repository.entity.Attorney;

@Transactional
public class BaseRepositoryTest extends AbstractBaseTest{

    /* Uses AttorneyRepository as an implementation of BaseRepository to test
     * BaseRepository methods that are inherited
     */
    
    @Autowired
    private AttorneyRepository repository;
    
    @Test
    public void findAll_WithValidPageable_ShouldReturnPage() throws Exception {
        Pageable request = PageRequest.of(0, 5);
        
        Page result = repository.findAll(request);
        
        assertThat(result).isNotNull();
        assertThat(result.hasContent());
        assertThat(result.getContent().get(0)).isInstanceOf(Attorney.class);
    }
    
    @Test
    public void save_WithNewEntity_ShouldSaveToDatabase() throws Exception {
        Attorney newAttorney = new Attorney.AttorneyBuilder().withId(null).build();
        
        Attorney result = repository.save(newAttorney);
        
        assertThat(result.getId()).isNotNull();
        assertThat(repository.findById(result.getId())).isNotNull();
    }
    
    @Test
    public void save_WithExistingEntity_ShouldUpdateInDatabase() throws Exception {
        Optional<Attorney> existingAttorney = repository.findById(1L);
        
        existingAttorney.get().setName("CHANGED TEST NAME");
        Attorney result = repository.save(existingAttorney.get());
        
        assertThat(result.getName()).isEqualTo("CHANGED TEST NAME");
    }
    
    @Test
    public void findById_WithValidId_ShouldReturnOptionalOfEntity() throws Exception {
        Optional<Attorney> result = repository.findById(1L);
        
        assertThat(result).isNotNull();
        assertThat(result.get()).isInstanceOf(Attorney.class);
    }

    @Test
    public void existsById_WithValidId_ShouldReturnTrue() throws Exception {
        boolean result = repository.existsById(1L);
        
        assertThat(result).isTrue();
    }
    
    @Test
    public void existsById_WithInvalidId_ShouldReturnFalse() throws Exception {
        boolean result = repository.existsById(500L);
        
        assertThat(result).isFalse();
    }

    @Test
    public void count_ShouldReturnCorrectCountOfEntities() throws Exception {
        long result = repository.count();
        
        assertThat(result).isEqualTo(6L);
    }

    @Test
    public void deleteById_WithValidId_ShouldDeleteEntity() throws Exception {
        repository.deleteById(5L);
        
        assertThat(repository.existsById(5l)).isFalse();
    }

    @Test
    public void delete_WithValidEntity_ShouldDeleteEntity() throws Exception {
        Optional<Attorney> target = repository.findById(5L);
        repository.delete(target.get());
        
        assertThat(repository.existsById(5L)).isFalse();
    }

    @Test
    public void deleteAll_ShouldDeleteSelectedEntities() throws Exception {    
        List<Attorney> targets = new ArrayList<>();
        targets.add(repository.findById(5L).get());
        targets.add(repository.findById(6L).get());
        
        
        repository.deleteAll(targets);
        
        assertThat(repository.existsById(5L)).isFalse();
        assertThat(repository.existsById(6L)).isFalse();
    }

    @Test
    public void findAll_ShouldReturnListOfEntities() throws Exception {
        List<Attorney> result = repository.findAll();
        
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(6);
        assertThat(result.get(0)).isInstanceOf(Attorney.class);
    }
}
