CREATE TABLE user(
 id int(11) NOT NULL AUTO_INCREMENT,
 username varchar(20),
 password varchar(20),
 PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户表';


CREATE TABLE role (
  id int(11) NOT NULL AUTO_INCREMENT,
  rolename varchar(50) DEFAULT NULL,
  alias varchar(50) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='权限 - 角色表';

CREATE TABLE user_role(
  id int(11) NOT NULL AUTO_INCREMENT,
  user_id int,
  role_id int,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户和权限对应表';