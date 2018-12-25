CREATE TABLE IF NOT EXISTS arigatos(
  arigato_id int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID'
  , from_id int(11) NOT NULL COMMENT '送信元ID'
  , to_id int(11) NOT NULL COMMENT '送信先ID'
  , subject TEXT NOT NULL COMMENT '件名'
  , body TEXT NOT NULL COMMENT '本体'
  , created_by VARCHAR(50) NOT NULL COMMENT '登録者'
  , created_at DATETIME NOT NULL COMMENT '登録日時'
  , updated_by VARCHAR(50) DEFAULT NULL COMMENT '更新者'
  , updated_at DATETIME DEFAULT NULL COMMENT '更新日時'
  , deleted_by VARCHAR(50) DEFAULT NULL COMMENT '削除者'
  , deleted_at DATETIME DEFAULT NULL COMMENT '削除日時'
  , version INT(11) unsigned NOT NULL DEFAULT 1 COMMENT '改訂番号'
  , PRIMARY KEY (arigato_id)
) COMMENT='ありがと';

CREATE TABLE IF NOT EXISTS arigato_images(
  arigato_image_id int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID'
  , arigato_id int(11) unsigned NOT NULL COMMENT 'ありがとID'
  , upload_file_id INT(11) unsigned NOT NULL COMMENT '添付ファイルID'
  , created_by VARCHAR(50) NOT NULL COMMENT '登録者'
  , created_at DATETIME NOT NULL COMMENT '登録日時'
  , updated_by VARCHAR(50) DEFAULT NULL COMMENT '更新者'
  , updated_at DATETIME DEFAULT NULL COMMENT '更新日時'
  , deleted_by VARCHAR(50) DEFAULT NULL COMMENT '削除者'
  , deleted_at DATETIME DEFAULT NULL COMMENT '削除日時'
  , version INT(11) unsigned NOT NULL DEFAULT 1 COMMENT '改訂番号'
  , PRIMARY KEY (arigato_image_id)
) COMMENT='ありがとイメージ';
