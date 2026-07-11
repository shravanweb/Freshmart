alter table if exists _product add column if not exists _vendor_id int8;

alter table if exists _product
  drop constraint if exists fk_product_vendor;

alter table if exists _product
  add constraint fk_product_vendor
  foreign key (_vendor_id) references _vendor deferrable initially deferred;

update _product p
set _vendor_id = latest._vendor_id
from (
  select distinct on (pol._product_id)
    pol._product_id,
    po._vendor_id
  from _purchase_order_line pol
  inner join _purchase_order po on po._id = pol._purchase_order_id
  order by pol._product_id, po._order_date desc, po._id desc
) latest
where p._id = latest._product_id
  and p._vendor_id is null;
