package ru.devtron.republicperi.data.entities;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;

import ru.devtron.republicperi.data.network.response.PlaceRes;

@Entity(active = true, nameInDb = "PLACES")
public class PlaceEntity {
    @Id(autoincrement = true)
    private Long id;
    @Unique
    @NotNull
    private Long remoteId;
    private String desc;
    private String img;
    private String warning;
    private String way;
    private String secure;
    private Double lat;
    private Double lng;
    @ToOne(joinProperty = "remoteId")
    private CityEntity city;

    public PlaceEntity(PlaceRes placeRes) {
        this.remoteId = placeRes.getId();
        this.desc = placeRes.getDesc();
        this.img = placeRes.getImg();
        this.warning = placeRes.getWarning();
        this.way = placeRes.getWay();
        this.secure = placeRes.getSecure();
        lat = placeRes.getLat();
        lng = placeRes.getLng();
        this.city = new CityEntity(placeRes.getCity());
    }

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 79395075)
    private transient PlaceEntityDao myDao;
    @Generated(hash = 1696970556)
    private transient Long city__resolvedKey;

    @Generated(hash = 973987082)
    public PlaceEntity(Long id, @NotNull Long remoteId, String desc, String img, String warning,
            String way, String secure, Double lat, Double lng) {
        this.id = id;
        this.remoteId = remoteId;
        this.desc = desc;
        this.img = img;
        this.warning = warning;
        this.way = way;
        this.secure = secure;
        this.lat = lat;
        this.lng = lng;
    }

    @Generated(hash = 1078778487)
    public PlaceEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    public Long getRemoteId() {
        return this.remoteId;
    }

    public void setRemoteId(Long remoteId) {
        this.remoteId = remoteId;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getWarning() {
        return this.warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String getWay() {
        return this.way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getSecure() {
        return this.secure;
    }

    public void setSecure(String secure) {
        this.secure = secure;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1934630583)
    public CityEntity getCity() {
        Long __key = this.remoteId;
        if (city__resolvedKey == null || !city__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CityEntityDao targetDao = daoSession.getCityEntityDao();
            CityEntity cityNew = targetDao.load(__key);
            synchronized (this) {
                city = cityNew;
                city__resolvedKey = __key;
            }
        }
        return city;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1789463335)
    public void setCity(@NotNull CityEntity city) {
        if (city == null) {
            throw new DaoException(
                    "To-one property 'remoteId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.city = city;
            remoteId = city.getId();
            city__resolvedKey = remoteId;
        }
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1784862713)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPlaceEntityDao() : null;
    }

    
}
