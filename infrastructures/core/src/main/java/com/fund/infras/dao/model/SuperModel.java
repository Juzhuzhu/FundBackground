package com.fund.infras.dao.model;

import com.baomidou.mybatisplus.annotation.*;
import com.google.common.base.Objects;

import java.time.LocalDateTime;

/**
 * 实体类通用字段
 * <p>
 * Create at 2023/02/24 22:19
 *
 * @author 罗康明
 * @version 1.0.0, 2023/02/24
 * @since 1.0.0
 */
public abstract class SuperModel<T extends SuperModel<?>> {
    @TableId(type = IdType.INPUT)
    private String id;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime utcCreate;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime utcUpdated;
    @TableLogic("0")
    @TableField(fill = FieldFill.UPDATE)
    private Long utcDeleted;

    public SuperModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getUtcCreate() {
        return utcCreate;
    }

    public void setUtcCreate(LocalDateTime utcCreate) {
        this.utcCreate = utcCreate;
    }

    public LocalDateTime getUtcUpdated() {
        return utcUpdated;
    }

    public void setUtcUpdated(LocalDateTime utcUpdated) {
        this.utcUpdated = utcUpdated;
    }

    public Long getUtcDeleted() {
        return utcDeleted;
    }

    public void setUtcDeleted(Long utcDeleted) {
        this.utcDeleted = utcDeleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SuperModel<?> that = (SuperModel<?>) o;
        return Objects.equal(id, that.id) && Objects.equal(utcCreate, that.utcCreate) && Objects.equal(utcUpdated, that.utcUpdated) && Objects.equal(utcDeleted, that.utcDeleted);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, utcCreate, utcUpdated, utcDeleted);
    }
}
