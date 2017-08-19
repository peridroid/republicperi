package ru.devtron.republicperi.data.entities;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

import ru.devtron.republicperi.data.network.response.City;

@Entity(active = true, nameInDb = "CITIES")
public class CityEntity {
    @Id
    private Long id;
    @Unique
    @NotNull
    private Long remoteId;
    private Double lat;
    private Double lng;
    private String name;


    public CityEntity(City city) {
        this.remoteId = city.id;
        lat = city.lat;
        lng = city.lng;
        name = city.name;
    }

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1928598513)
    private transient CityEntityDao myDao;
    @Generated(hash = 1012856246)
    public CityEntity(Long id, @NotNull Long remoteId, Double lat, Double lng,
            String name) {
        this.id = id;
        this.remoteId = remoteId;
        this.lat = lat;
        this.lng = lng;
        this.name = name;
    }
    @Generated(hash = 2001321047)
    public CityEntity() {
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getRemoteId() {
        return this.remoteId;
    }
    public void setRemoteId(Long remoteId) {
        this.remoteId = remoteId;
    }
    public Double getLat() {
        return this.lat;
    }
    public void setLat(Double lat) {
        this.lat = lat;
    }
    public Double getLng() {
        return this.lng;
    }
    public void setLng(Double lng) {
        this.lng = lng;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
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
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 564858177)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCityEntityDao() : null;
    }
}
