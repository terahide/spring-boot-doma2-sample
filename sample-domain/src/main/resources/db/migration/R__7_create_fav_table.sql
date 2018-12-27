CREATE TABLE IF NOT EXISTS favorites(
  favorite_id int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID'
  , arigato_id int(11) unsigned NOT NULL COMMENT 'ありがとID'
  , user_id INT(11) unsigned NOT NULL COMMENT 'ユーザID'
  , created_by VARCHAR(50) NOT NULL COMMENT '登録者'
  , created_at DATETIME NOT NULL COMMENT '登録日時'
  , updated_by VARCHAR(50) DEFAULT NULL COMMENT '更新者'
  , updated_at DATETIME DEFAULT NULL COMMENT '更新日時'
  , deleted_by VARCHAR(50) DEFAULT NULL COMMENT '削除者'
  , deleted_at DATETIME DEFAULT NULL COMMENT '削除日時'
  , version INT(11) unsigned NOT NULL DEFAULT 1 COMMENT '改訂番号'
  , PRIMARY KEY (favorite_id)
) COMMENT='いいね';
