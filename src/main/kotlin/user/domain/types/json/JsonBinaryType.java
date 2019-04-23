package user.domain.types.json;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.usertype.DynamicParameterizedType;

import java.util.Properties;

/**
 * Create by lucy on 2019-04-02
 **/
public class JsonBinaryType
        extends AbstractSingleColumnStandardBasicType<Object> implements DynamicParameterizedType {

    public JsonBinaryType() {
        super( JsonBinarySqlTypeDescriptor.INSTANCE, new JsonTypeDescriptor());
    }

    public String getName() {
        return "jsonb";
    }

    @Override
    public void setParameterValues(Properties parameters) {
        ((JsonTypeDescriptor) getJavaTypeDescriptor()).setParameterValues(parameters);
    }

}
