package xyz.luomu32.rdep.project.service;

import xyz.luomu32.rdep.project.pojo.dubbo.RuleCreateCmd;

public interface DubboAdminService {

    void createRule(RuleCreateCmd ruleCreateCmd);
}
