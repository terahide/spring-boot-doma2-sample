select * from upload_files
where upload_file_id in (
    select upload_file_id from arigato_images
    where arigato_id = /* arigatoId */1
)