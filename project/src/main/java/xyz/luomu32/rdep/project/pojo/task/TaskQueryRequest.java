package xyz.luomu32.rdep.project.pojo.task;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import xyz.luomu32.rdep.common.jpa.EqualsSpecification;
import xyz.luomu32.rdep.common.web.DateRange;
import xyz.luomu32.rdep.project.model.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class TaskQueryRequest {

    //TODO 感觉这种方式是旁门左道，由ModelAttributeMethodProcessor负责ArgumentResolver，
    //1。通过构造方法实例化
    //2。Request获取参数让入List，将参数一一尝试设置到参数对象
    //
//    @ConstructorProperties({"startStart", "startEnd"})
    public TaskQueryRequest(LocalDate startStart, LocalDate startEnd, DateRange end) {
        this.start = new DateRange(startStart, startEnd);
        if (null == end)
            this.end = DateRange.empty();
        else
            this.end = end;
    }

    private Long projectId;

    private Long moduleId;

    private Long chargerId;

    private String state;

    private DateRange start;

    private DateRange end;

    private String title;

    private Integer priority;

    private Integer progress;

    public Optional<Specification<Task>> buildSpec() {
        List<Specification<Task>> specifications = new ArrayList<>();
        if (null != projectId)
            specifications.add((Specification) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("projectId"), projectId));
        if (null != moduleId)
            specifications.add(new EqualsSpecification<Task>("moduleId", moduleId));

        return specifications.stream().reduce((spec1, spec2) -> spec1.and(spec2));
    }
}
