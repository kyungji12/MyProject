package kr.co.uclick.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserInfo is a Querydsl query type for UserInfo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserInfo extends EntityPathBase<UserInfo> {

    private static final long serialVersionUID = 780576036L;

    public static final QUserInfo userInfo = new QUserInfo("userInfo");

    public final ListPath<ContactInfo, QContactInfo> contacts = this.<ContactInfo, QContactInfo>createList("contacts", ContactInfo.class, QContactInfo.class, PathInits.DIRECT2);

    public final StringPath department = createString("department");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.util.Date> lastUpdate = createDateTime("lastUpdate", java.util.Date.class);

    public final StringPath name = createString("name");

    public final StringPath position = createString("position");

    public final DateTimePath<java.util.Date> regiDate = createDateTime("regiDate", java.util.Date.class);

    public final StringPath special = createString("special");

    public QUserInfo(String variable) {
        super(UserInfo.class, forVariable(variable));
    }

    public QUserInfo(Path<? extends UserInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserInfo(PathMetadata metadata) {
        super(UserInfo.class, metadata);
    }

}

