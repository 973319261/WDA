package com.gx.mapper;

import com.gx.po.FileType;
import org.springframework.stereotype.Repository;

/**
 * FileTypeDAO继承基类
 */
@Repository
public interface FileTypeDAO extends MyBatisBaseDao<FileType, Integer> {
}