package ${config.pkg}.${config.entityPkg};

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ${entityName} {

    <#list params as param>
        private ${param.fieldType} ${param.fieldName};

    </#list>

}