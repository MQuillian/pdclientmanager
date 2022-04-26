package com.pdclientmanager.documents;

import java.net.URL;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pdclientmanager.model.projection.CaseLightProjection;
import com.pdclientmanager.service.CaseService;

@Controller
public class DocumentController {
    
    private DocumentStorageService documentService;
    private CaseService caseService;
    
    @Autowired
    public DocumentController(DocumentStorageService documentService, CaseService caseService) {
        this.documentService = documentService;
        this.caseService = caseService;
    }

    @GetMapping("/documents/{caseNumber}/upload")
    public String showUploadDocumentForm(@PathVariable("caseNumber") String caseNumber, Model model,
            final RedirectAttributes redirectAttributes) {
        
        
        model.addAttribute("caseNumber", caseNumber);
        model.addAttribute("document", new Document()); 
        
        return "documents/fileUpload";
    }
    
    @PostMapping(path = "/documents/{caseNumber}/upload")
    public String uploadDocument(@PathVariable("caseNumber") String caseNumber,
            @ModelAttribute("document") @Valid Document document, BindingResult result,
            Model model, final RedirectAttributes redirectAttributes) {
            
        try {
            if(result.hasErrors()) {
                model.addAttribute("document", document);
                redirectAttributes.addFlashAttribute("css", "danger");
                redirectAttributes.addFlashAttribute("msg", "File and file name cannot be empty");
                
                return "redirect:/documents/" + caseNumber + "/upload";
                
            } else {
                documentService.uploadFile(caseNumber, document.getFileName(), document.getFile());
                
                redirectAttributes.addFlashAttribute("css", "success");
                redirectAttributes.addFlashAttribute("msg", "Document saved successfully!");
                Long id = caseService.findByCaseNumber(caseNumber, CaseLightProjection.class).getId();
                
                return "redirect:/cases/" + id;
            }
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("css", "danger");
            redirectAttributes.addFlashAttribute("msg", "Error with document storage");
        
            return "redirect:/documents/" + caseNumber + "/upload";
        }
    }
    
    @GetMapping("/documents/{caseNumber}/view/{fileName}")
    public String showUploadDocumentForm(@PathVariable("caseNumber") String caseNumber,
            @PathVariable("fileName") String fileName,
            final RedirectAttributes redirectAttributes) {
        
        try {
            URL url = documentService.getDownloadUrl(caseNumber, fileName);
            
            return "redirect:" + url.toString();
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("css", "danger");
            redirectAttributes.addFlashAttribute("msg", "Error with document retrieval");
        
            return "redirect:/cases";
        }
    }
    
    @GetMapping("/documents/{caseNumber}/list")
    public String showDocumentList(@PathVariable("caseNumber") String caseNumber, 
            Model model, final RedirectAttributes redirectAttributes) {
        
        try {
            List<String> documentNames = documentService.listFiles(caseNumber);
            model.addAttribute("documents", documentNames);
            
            return "documents/listDocuments";
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("css", "danger");
            redirectAttributes.addFlashAttribute("msg", "Error with document retrieval");
        
            return "redirect:/cases";
        }
    }
    
    @PostMapping("/documents/{caseNumber}/delete/{document}")
    public String deleteDocumentById(@PathVariable("caseNumber") String caseNumber,
            @PathVariable("document") String fileName,
            final RedirectAttributes redirectAttributes) {
        try {
            documentService.deleteFile(caseNumber, fileName);

            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", "Document is deleted!");
            
            Long id = caseService.findByCaseNumber(caseNumber, CaseLightProjection.class).getId();
            
            return "redirect:/cases/" + id;
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("css", "danger");
            redirectAttributes.addFlashAttribute("msg", "Error with document deletion");
            
            return "redirect:/documents/" + caseNumber + "/list";
        }
    }
}
