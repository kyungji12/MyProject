package kr.co.uclick.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QContactInfo is a Querydsl query type for ContactInfo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QContactInfo extends EntityPathBase<ContactInfo> {

    private static final long serialVersionUID = -415142141L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QContactInfo contactInfo = new QContactInfo("contactInfo");

    public final StringPath contact = createString("contact");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath type = createString("type");

    public final QUserInfo userId;

    public QContactInfo(String variable) {
        this(ContactInfo.class, forVariable(variable), INITS);
    }

    public QContactInfo(Path<? extends ContactInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QContactInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QContactInfo(PathMetadata metadata, PathInits inits) {
        this(ContactInfo.class, metadata, inits);
    }

    public QContactInfo(Class<? extends ContactInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.userId = inits.isInitialized("userId") ? new QUserInfo(forProperty("userId")) : null;
    }

}

