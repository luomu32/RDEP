package xyz.luomu32.rdep.project.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 机器资源的度量统计信息
 * 用于调度往该机器部署服务或中间件。
 * 如果资源紧张，不再部署服务
 */
@Data
@Entity
@Table(name = "t_machine_resource_metrics")
public class MachineResourceMetrics {
}
