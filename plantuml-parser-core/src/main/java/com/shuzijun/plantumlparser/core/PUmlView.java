package com.shuzijun.plantumlparser.core;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * PUml视图
 *
 * @author shuzijun
 */
public class PUmlView implements PUml {

    private List<PUmlClass> pUmlClassList = new ArrayList<>();

    private List<PUmlRelation> pUmlRelationList = new ArrayList<>();

    public void addPUmlClass(PUmlClass pUmlClass) {
        pUmlClassList.add(pUmlClass);
    }

    public void addPUmlRelation(PUmlRelation pUmlRelation) {
        pUmlRelationList.add(pUmlRelation);
    }

    @Override
    public String toString() {
        return "@startuml\n" +
                (pUmlClassList.isEmpty() ? "" : pUmlClassList.stream().map(pUmlClass -> pUmlClass.toString()).collect(Collectors.joining("\n")) + "\n") +
                (pUmlRelationList.isEmpty() ? "" : "\n\n" + pUmlRelationList.stream().filter(r -> isTargetInClass(r)).map(pUmlRelation -> pUmlRelation.toString()).collect(Collectors.joining("\n")) + "\n") +
                "@enduml"
                ;
    }

    private boolean isTargetInClass(PUmlRelation relation) {
        if (relation.getTarget() == null || relation.getTarget().trim().length() == 0) {
            return false;
        }

        String[] items = relation.getTarget().split(":")[0].split("\\.");
        String targetClassName = items[items.length - 1].trim();
        return pUmlClassList.stream().anyMatch(c -> c.getClassName().equals(targetClassName));
    }
}
