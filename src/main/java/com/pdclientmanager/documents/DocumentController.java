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

    @GetMapping("/documents/{id}/{caseNumber}/upload")
    public String showUploadDocumentForm(@PathVariable("id") String caseId,
            @PathVariable("caseNumber") String caseNumber, Model model,
            final RedirectAttributes redirectAttributes) {
        
        model.addAttribute("caseNumber", caseNumber);
        model.addAttribute("caseId", caseId);
        model.addAttribute("document", new Document()); 
        
        return "documents/fileUpload";
    }
    
    @PostMapping(path = "/documents/{id}/{caseNumber}/upload")
    public String uploadDocument(@PathVariable("id") String caseId,
            @PathVariable("caseNumber") String caseNumber,
            @ModelAttribute("document") @Valid Document document, BindingResult result,
            Model model, final RedirectAttributes redirectAttributes) {
            
        try {
            if(result.hasErrors()) {
                model.addAttribute("document", document);
                redirectAttributes.addFlashAttribute("css", "danger");
                redirectAttributes.addFlashAttribute("msg", "File and file name cannot be empty");
                
                return "redirect:/documents/" + caseId + "/" + caseNumber + "/upload";
                
            } else {
                documentService.uploadFile(caseId, document.getFileName(), document.getFile());
                
                redirectAttributes.addFlashAttribute("css", "success");
                redirectAttributes.addFlashAttribute("msg", "Document saved successfully!");
                
                return "redirect:/cases/" + caseId;
            }
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("css", "danger");
            redirectAttributes.addFlashAttribute("msg", "Error with document storage");
        
            return "redirect:/documents/" + caseId + "/upload";
        }
    }
    
    @GetMapping("/documents/{id}/view/{fileName}")
    public String showDocument(@PathVariable("id") String caseId,
            @PathVariable("fileName") String fileName,
            final RedirectAttributes redirectAttributes) {
        
        try {
            URL url = documentService.getDownloadUrl(caseId, fileName);
            
            return "redirect:" + url.toString();
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("css", "danger");
            redirectAttributes.addFlashAttribute("msg", "Error with document retrieval");
        
            return "redirect:/cases";
        }
    }
    
    @GetMapping("/documents/{id}/{caseNumber}/list")
    public String showDocumentList(@PathVariable("id") String caseId, 
            @PathVariable("caseNumber") String caseNumber,
            Model model, final RedirectAttributes redirectAttributes) {
        
        try {
            List<String> documentNames = documentService.listFiles(caseId);
            model.addAttribute("caseId", caseId);
            model.addAttribute("caseNumber", caseNumber);
            model.addAttribute("documents", documentNames);
            
            return "documents/listDocuments";
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("css", "danger");
            redirectAttributes.addFlashAttribute("msg", "Error with document retrieval");
        
            return "redirect:/cases";
        }
    }
    
    @PostMapping("/documents/{id}/delete/{document}")
    public String deleteDocumentById(@PathVariable("id") String caseId,
            @PathVariable("document") String fileName,
            final RedirectAttributes redirectAttributes) {
        try {
            documentService.deleteFile(caseId, fileName);

            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", "Document is deleted!");
            
            Long id = caseService.findByCaseNumber(caseId, CaseLightProjection.class).getId();
            
            return "redirect:/cases/" + id;
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("css", "danger");
            redirectAttributes.addFlashAttribute("msg", "Error with document deletion");
            
            return "redirect:/documents/" + caseId + "/list";
        }
    }
}
