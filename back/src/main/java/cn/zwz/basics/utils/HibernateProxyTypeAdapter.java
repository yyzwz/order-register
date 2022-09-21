package cn.zwz.basics.utils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import java.io.IOException;

/**
 * 代理对象实例化类
 * @author 郑为中
 */
public class HibernateProxyTypeAdapter extends TypeAdapter<HibernateProxy> {

    @ApiModelProperty(value = "GSON数据")
    private final Gson context;

    @Override
    public HibernateProxy read(JsonReader reader) {
        throw new UnsupportedOperationException("UnsupportedOperationException");
    }

    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
        @Override
        @SuppressWarnings("unchecked")
        public <T> TypeAdapter<T> create(Gson gsonObj, TypeToken<T> type) {
            if(HibernateProxy.class.isAssignableFrom(type.getRawType())) {
                return (TypeAdapter<T>) new HibernateProxyTypeAdapter(gsonObj);
            }
            return null;
        }
    };

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void write(JsonWriter w, HibernateProxy hibernateAgent) throws IOException {
        if (hibernateAgent == null) {
            w.nullValue();
            return;
        }
        Class<?> HibernateClass = Hibernate.getClass(hibernateAgent);
        TypeAdapter typeAdapter = context.getAdapter(TypeToken.get(HibernateClass));
        Object hibernateProxy = ((HibernateProxy) hibernateAgent).getHibernateLazyInitializer().getImplementation();
        typeAdapter.write(w, hibernateProxy);
    }

    private HibernateProxyTypeAdapter(Gson gsonContext) {
        this.context = gsonContext;
    }
}