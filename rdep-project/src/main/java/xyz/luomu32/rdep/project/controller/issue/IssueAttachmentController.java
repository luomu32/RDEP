package xyz.luomu32.rdep.project.controller.issue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.luomu32.rdep.project.pojo.IssueAttachmentResponse;
import xyz.luomu32.rdep.project.service.IssueService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("projects/{projectId}/modules/{moduleId}/issues/{issueId}/attachments")
public class IssueAttachmentController {

    @Autowired
    private IssueService issueService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<IssueAttachmentResponse> createAttachments(@PathVariable Long issueId,
                                                           @PathVariable Long projectId,
                                                           @PathVariable Long moduleId,
                                                           @RequestParam Map<String, MultipartFile> files) {
        //TODO Map可以获取到上传的文件，换成List就为空，获取不到了
        //IView的upload组件，虽然可以多选图片上传，但是还是没个图片发起一个请求，所以files基本都只有一个
        return issueService.createAttachment(issueId, projectId, moduleId, files);
    }

    @DeleteMapping("{attachmentId}")
    public void deleteAttachment(@PathVariable Long projectId,
                                 @PathVariable Long moduleId,
                                 @PathVariable Long issueId,
                                 @PathVariable Long attachmentId) {
        issueService.deleteAttachment(projectId, moduleId, issueId, attachmentId);
    }

    @GetMapping()
    public List<IssueAttachmentResponse> fetchAttachments(@PathVariable Long projectId,
                                                          @PathVariable Long moduleId,
                                                          @PathVariable Long issueId) {
        return issueService.fetchAttachment(projectId, moduleId, issueId);
    }

    @GetMapping(value = "{attachmentId}.{ext}")
    public ResponseEntity<byte[]> fetchAttachment(@PathVariable Long projectId,
                                                  @PathVariable Long moduleId,
                                                  @PathVariable Long issueId,
                                                  @PathVariable Long attachmentId,
                                                  @PathVariable String ext) {
        byte[] content = issueService.fetchAttachment(projectId, moduleId, issueId, attachmentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE)
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(content.length))
                .cacheControl(CacheControl.maxAge(30, TimeUnit.DAYS))
                .body(content);
    }

}
