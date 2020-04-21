package xyz.luomu32.rdep.project.model;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Task.class)
public abstract class Task_ extends xyz.luomu32.rdep.common.jpa.BaseEntity_ {

	public static volatile SingularAttribute<Task, String> moduleName;
	public static volatile SingularAttribute<Task, Long> chargerId;
	public static volatile SingularAttribute<Task, String> chargerName;
	public static volatile SingularAttribute<Task, LocalDate> start;
	public static volatile SingularAttribute<Task, Integer> progress;
	public static volatile SingularAttribute<Task, LocalDate> end;
	public static volatile SingularAttribute<Task, Long> id;
	public static volatile SingularAttribute<Task, TaskState> state;
	public static volatile SingularAttribute<Task, String> title;
	public static volatile SingularAttribute<Task, Long> moduleId;
	public static volatile SingularAttribute<Task, Integer> priority;
	public static volatile SingularAttribute<Task, Long> projectId;

	public static final String MODULE_NAME = "moduleName";
	public static final String CHARGER_ID = "chargerId";
	public static final String CHARGER_NAME = "chargerName";
	public static final String START = "start";
	public static final String PROGRESS = "progress";
	public static final String END = "end";
	public static final String ID = "id";
	public static final String STATE = "state";
	public static final String TITLE = "title";
	public static final String MODULE_ID = "moduleId";
	public static final String PRIORITY = "priority";
	public static final String PROJECT_ID = "projectId";

}

