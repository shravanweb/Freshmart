create sequence _d3e_sequence;
create table _dfile ( _id varchar(255) not null, _name varchar(255), _size int8 not null, _mime_type varchar(255), _count int8 not null, primary key (_id) );

create table _anonymous_user(_id int8 not null, _save_status int4, primary key (_id));

create table _audit_log(_id int8 not null, _save_status int4, _entity_type varchar(255) not null, _entity_id varchar(255) not null, _action varchar(255) not null, _performed_by_id int8 not null, _performed_at timestamp not null, _old_values text, _new_values text, _ip_address varchar(255), _user_agent varchar(255), _organization_id int8 not null, primary key (_id));

create table _avatar(_id int8 not null, _save_status int4, _image_size int8 not null, _image_width int8 not null, _image_height int8 not null, _image_file_id varchar(255), _create_from varchar(255), primary key (_id));

create table _base_user(_id int8 not null, _save_status int4, _is_active bool, _devices_id int8, primary key (_id));

create table _base_user_session(_id int8 not null, _save_status int4, _user_session_id text not null, primary key (_id));

create table _goods_receipt(_id int8 not null, _save_status int4, _receipt_number varchar(255) not null, _purchase_order_id int8, _vendor_id int8 not null, _warehouse_id int8 not null, _receipt_date date not null, _status varchar(255) not null, _notes text, _received_by_id int8, _created_at timestamp, _organization_id int8 not null, primary key (_id));

create table _goods_receipt_line(_id int8 not null, _save_status int4, _product_id int8 not null, _received_quantity float8 not null, _unit_cost float8 not null, _batch_number varchar(255), _expiry_date date, _purchase_order_line_id int8, _goods_receipt_id int8 not null, primary key (_id));

create table _in_app_notification(_id int8 not null, _save_status int4, _recipient_id int8 not null, _title varchar(255) not null, _message text not null, _notification_type varchar(255) not null, _reference_type varchar(255), _reference_id varchar(255), _is_read bool default 'false', _created_at timestamp, _organization_id int8 not null, primary key (_id));

create table _inventory_adjustment(_id int8 not null, _save_status int4, _adjustment_number varchar(255) not null, _warehouse_id int8 not null, _adjustment_date date not null, _reason varchar(255) not null, _status varchar(255) not null, _notes text, _adjusted_by_id int8, _organization_id int8 not null, primary key (_id));

create table _inventory_adjustment_line(_id int8 not null, _save_status int4, _product_id int8 not null, _quantity_before float8, _quantity_change float8 not null, _quantity_after float8, _batch_number varchar(255), _unit_cost float8, _inventory_adjustment_id int8 not null, primary key (_id));

create table _inventory_movement(_id int8 not null, _save_status int4, _movement_number varchar(255) not null, _warehouse_id int8 not null, _product_id int8 not null, _movement_type varchar(255) not null, _quantity float8 not null, _direction varchar(255) not null, _reference_type varchar(255) not null, _reference_id varchar(255) not null, _batch_number varchar(255), _unit_cost float8, _balance_after float8 not null, _movement_date timestamp not null, _performed_by_id int8, _notes text, _organization_id int8 not null, primary key (_id));

create table _notification_template(_id int8 not null, _save_status int4, _template_code varchar(255) not null, _channel varchar(255) not null, _subject varchar(255), _body_template text not null, _status varchar(255) not null, _organization_id int8 not null, primary key (_id));

create table _one_time_password(_id int8 not null, _save_status int4, _input varchar(255) not null, _input_type varchar(255) not null, _user_type varchar(255) not null, _success bool not null, _error_msg varchar(255), _token varchar(255), _code varchar(255), _user_id int8, _expiry timestamp, primary key (_id));

create table _organization(_id int8 not null, _save_status int4, _name varchar(255) not null, _code varchar(255) not null, _legal_name varchar(255), _tax_id varchar(255), _email varchar(255), _phone varchar(255), _address text, _logo_id varchar(255), _currency varchar(255) default 'USD' not null, _timezone varchar(255) default 'UTC', _status varchar(255) not null, _created_at timestamp, _updated_at timestamp, _created_by_id int8, primary key (_id));

create table _product(_id int8 not null, _save_status int4, _category_id int8 not null, _sku varchar(255) not null, _name varchar(255) not null, _description text, _barcode varchar(255), _base_uom_id int8 not null, _purchase_price float8 default '0.0', _selling_price float8 default '0.0', _reorder_level float8 default '0.0', _reorder_quantity float8 default '0.0', _track_batch bool default 'false', _track_expiry bool default 'false', _shelf_life_days int8, _image_id varchar(255), _status varchar(255) not null, _created_at timestamp, _updated_at timestamp, _organization_id int8 not null, primary key (_id));

create table _product_category(_id int8 not null, _save_status int4, _parent_category_id int8, _name varchar(255) not null, _code varchar(255) not null, _description text, _status varchar(255) not null, _organization_id int8 not null, primary key (_id));

create table _purchase_order(_id int8 not null, _save_status int4, _po_number varchar(255) not null, _vendor_id int8 not null, _warehouse_id int8 not null, _order_date date not null, _expected_delivery_date date, _status varchar(255) not null, _subtotal float8, _tax_amount float8 default '0.0', _total_amount float8, _notes text, _created_by_id int8, _approved_by_id int8, _approved_at timestamp, _created_at timestamp, _organization_id int8 not null, primary key (_id));

create table _purchase_order_line(_id int8 not null, _save_status int4, _line_number int8 not null, _product_id int8 not null, _ordered_quantity float8 not null, _received_quantity float8 default '0.0', _unit_price float8 not null, _line_total float8, _uom_id int8 not null, _purchase_order_id int8 not null, primary key (_id));

create table _push_notification(_id int8 not null, _save_status int4, _skip_this_device bool, _device_token text, _title varchar(255), _body text, _path varchar(255), _failed bool, primary key (_id));

create table _push_notification_users_fab88b(_push_notification_id int8 not null, _users_id int8 not null, _users_order int4 not null, primary key (_push_notification_id, _users_order));

create table _push_notification_data_9b2135(_push_notification_id int8 not null, _data text not null);

create table _push_notification_failed_devices_d63aa4(_push_notification_id int8 not null, _failed_devices_id int8 not null, _failed_devices_order int4 not null, primary key (_push_notification_id, _failed_devices_order));

create table _report(_id int8 not null, _save_status int4, _report_doc text, primary key (_id));

create table _report_config(_id int8 not null, _save_status int4, _identity varchar(255) not null, primary key (_id));

create table _report_config_values_a912b7(_report_config_id int8 not null, _values_id int8 not null, _values_order int4 not null, primary key (_report_config_id, _values_order));

create table _report_config_option(_id int8 not null, _save_status int4, _identity varchar(255) not null, _value varchar(255) not null, primary key (_id));

create table _sales_order(_id int8 not null, _save_status int4, _store_id int8 not null, _warehouse_id int8 not null, _order_number varchar(255) not null, _order_date timestamp not null, _customer_name varchar(255), _customer_phone varchar(255), _status varchar(255) not null, _subtotal float8, _discount_amount float8 default '0.0', _tax_amount float8 default '0.0', _total_amount float8, _payment_status varchar(255), _sold_by_id int8, _organization_id int8 not null, primary key (_id));

create table _sales_order_line(_id int8 not null, _save_status int4, _product_id int8 not null, _quantity float8 not null, _unit_price float8 not null, _discount float8 default '0.0', _line_total float8, _batch_number varchar(255), _sales_order_id int8 not null, primary key (_id));

create table _sales_return(_id int8 not null, _save_status int4, _sales_order_id int8, _store_id int8 not null, _warehouse_id int8 not null, _return_number varchar(255) not null, _return_date timestamp not null, _status varchar(255) not null, _reason varchar(255) not null, _refund_amount float8, _processed_by_id int8, _organization_id int8 not null, primary key (_id));

create table _sales_return_line(_id int8 not null, _save_status int4, _product_id int8 not null, _quantity float8 not null, _unit_price float8 not null, _line_total float8, _batch_number varchar(255), _sales_return_id int8 not null, primary key (_id));

create table _stock_alert(_id int8 not null, _save_status int4, _warehouse_id int8 not null, _product_id int8 not null, _alert_type varchar(255) not null, _current_quantity float8 not null, _threshold float8, _expiry_date date, _status varchar(255) not null, _acknowledged_by_id int8, _acknowledged_at timestamp, _created_at timestamp, _organization_id int8 not null, primary key (_id));

create table _stock_batch(_id int8 not null, _save_status int4, _product_id int8 not null, _warehouse_id int8 not null, _batch_number varchar(255) not null, _quantity_on_hand float8 default '0.0' not null, _manufacturing_date date, _expiry_date date, _unit_cost float8 default '0.0', _status varchar(255) not null, _created_at timestamp, _organization_id int8 not null, primary key (_id));

create table _stock_transfer(_id int8 not null, _save_status int4, _transfer_number varchar(255) not null, _source_warehouse_id int8 not null, _destination_warehouse_id int8 not null, _transfer_date date not null, _status varchar(255) not null, _notes text, _requested_by_id int8, _approved_by_id int8, _shipped_at timestamp, _received_at timestamp, _organization_id int8 not null, primary key (_id));

create table _stock_transfer_line(_id int8 not null, _save_status int4, _product_id int8 not null, _quantity float8 not null, _batch_number varchar(255), _uom_id int8 not null, _stock_transfer_id int8 not null, primary key (_id));

create table _store(_id int8 not null, _save_status int4, _name varchar(255) not null, _code varchar(255) not null, _store_type varchar(255) not null, _address text, _phone varchar(255), _email varchar(255), _manager_id int8, _default_warehouse_id int8, _status varchar(255) not null, _created_at timestamp, _updated_at timestamp, _organization_id int8 not null, primary key (_id));

create table _supplier_contact(_id int8 not null, _save_status int4, _name varchar(255) not null, _email varchar(255), _phone varchar(255), _is_primary bool default 'false', _vendor_id int8 not null, primary key (_id));

create table _unit_of_measure(_id int8 not null, _save_status int4, _name varchar(255) not null, _symbol varchar(255) not null, _uom_type varchar(255) not null, _base_factor float8 default '1.0', _status varchar(255) not null, _organization_id int8 not null, primary key (_id));

create table _user(_id int8 not null, _save_status int4, _email varchar(255) not null, _password varchar(255) not null, _role varchar(255) not null, _status varchar(255) not null, _organization_id int8 not null, primary key (_id));

create table _user_device(_id int8 not null, _save_status int4, _user_id int8, _device_token text, primary key (_id));

create table _user_invitation(_id int8 not null, _save_status int4, _email varchar(255) not null, _app_role varchar(255) not null, _invited_by_id int8 not null, _token varchar(255) not null, _status varchar(255) not null, _expires_at timestamp not null, _accepted_at timestamp, _organization_id int8 not null, primary key (_id));

create table _user_login_record(_id int8 not null, _save_status int4, _user_id int8, _time_stamp timestamp, _ip_address varchar(255), _browser varchar(255), _device varchar(255), _location varchar(255), _success bool, _failure_reason varchar(255), primary key (_id));

create table _user_profile(_id int8 not null, _save_status int4, _user_id int8 not null, _display_name varchar(255) not null, _phone varchar(255), _avatar_id varchar(255), _app_role varchar(255) not null, _status varchar(255) not null, _last_login_at timestamp, _organization_id int8 not null, primary key (_id));

create table _user_profile_assigned_stores_dd1471(_user_profile_id int8 not null, _assigned_stores_id int8 not null, _assigned_stores_order int4 not null, primary key (_user_profile_id, _assigned_stores_order));

create table _user_profile_assigned_warehouses_17ffb1(_user_profile_id int8 not null, _assigned_warehouses_id int8 not null, _assigned_warehouses_order int4 not null, primary key (_user_profile_id, _assigned_warehouses_order));

create table _user_role(_id int8 not null, _save_status int4, _name varchar(255) not null, _role_code varchar(255) not null, _description text, _is_system bool default 'false', _permissions text, _organization_id int8 not null, primary key (_id));

create table _vendor(_id int8 not null, _save_status int4, _name varchar(255) not null, _code varchar(255) not null, _contact_person varchar(255), _email varchar(255), _phone varchar(255), _address text, _payment_terms varchar(255), _tax_id varchar(255), _rating int8, _status varchar(255) not null, _created_at timestamp, _organization_id int8 not null, primary key (_id));

create table _verification_data(_id int8 not null, _save_status int4, _method varchar(255) not null, _context varchar(255) not null, _token varchar(255), _subject varchar(255), _body varchar(255) not null, _processed bool, primary key (_id));

create table _warehouse(_id int8 not null, _save_status int4, _store_id int8, _name varchar(255) not null, _code varchar(255) not null, _warehouse_type varchar(255) not null, _address text, _is_default bool default 'false', _status varchar(255) not null, _created_at timestamp, _updated_at timestamp, _organization_id int8 not null, primary key (_id));

create table _warehouse_stock(_id int8 not null, _save_status int4, _warehouse_id int8 not null, _product_id int8 not null, _quantity_on_hand float8 default '0.0' not null, _quantity_reserved float8 default '0.0', _quantity_available float8, _average_cost float8 default '0.0', _stock_value float8, _last_movement_at timestamp, _low_stock_notified_at timestamp, _organization_id int8 not null, primary key (_id));

alter table if exists _report_config_values_a912b7 drop constraint if exists UK_f63b8ce4f8377eeedcf87d62bd06f43c;
alter table if exists _report_config_values_a912b7 add constraint UK_f63b8ce4f8377eeedcf87d62bd06f43c unique (_values_id) DEFERRABLE INITIALLY DEFERRED;

alter table if exists _anonymous_user add constraint FKeade347c9b950d74e0769e3329c0848a foreign key (_id) references _base_user DEFERRABLE INITIALLY DEFERRED;

alter table if exists _audit_log add constraint FK0f6f1b6e53e36545e2611a7f4562e394 foreign key (_performed_by_id) references _user DEFERRABLE INITIALLY DEFERRED;

alter table if exists _audit_log add constraint FK4a5a6b0edb3827713d67836c67cba0d6 foreign key (_organization_id) references _organization DEFERRABLE INITIALLY DEFERRED;

alter table if exists _base_user add constraint FK2c73cc66158fad166ceac4e0e59904c2 foreign key (_devices_id) references _user_device DEFERRABLE INITIALLY DEFERRED;

alter table if exists _goods_receipt add constraint FK04aee4011450d72bcb8ff8417f5c4a4d foreign key (_purchase_order_id) references _purchase_order DEFERRABLE INITIALLY DEFERRED;

alter table if exists _goods_receipt add constraint FKd3ba82c5bc78a1adf064ee68481de198 foreign key (_vendor_id) references _vendor DEFERRABLE INITIALLY DEFERRED;

alter table if exists _goods_receipt add constraint FK1e0027c43d5cb7684a7c73cd22bc2256 foreign key (_warehouse_id) references _warehouse DEFERRABLE INITIALLY DEFERRED;

alter table if exists _goods_receipt add constraint FK78c364b31651a13f00d976d02bc68efa foreign key (_received_by_id) references _user DEFERRABLE INITIALLY DEFERRED;

alter table if exists _goods_receipt add constraint FKbe45849a80221d56b6ecf7d47ca4c3a9 foreign key (_organization_id) references _organization DEFERRABLE INITIALLY DEFERRED;

alter table if exists _goods_receipt_line add constraint FK1200899144d9e3253188080f15e0063d foreign key (_product_id) references _product DEFERRABLE INITIALLY DEFERRED;

alter table if exists _goods_receipt_line add constraint FK9e2a543a62bac670ccd4747b50808f29 foreign key (_purchase_order_line_id) references _purchase_order_line DEFERRABLE INITIALLY DEFERRED;

alter table if exists _goods_receipt_line add constraint FK6ec8c8fb43f3439d4e5fbd1accf8ab1d foreign key (_goods_receipt_id) references _goods_receipt DEFERRABLE INITIALLY DEFERRED;

alter table if exists _in_app_notification add constraint FKe913effc6a2905f1755168d74801db1b foreign key (_recipient_id) references _user DEFERRABLE INITIALLY DEFERRED;

alter table if exists _in_app_notification add constraint FKb3b7c57911d2b9d27fe36d4f4b001148 foreign key (_organization_id) references _organization DEFERRABLE INITIALLY DEFERRED;

alter table if exists _inventory_adjustment add constraint FK4cce566b41775712ac4b7a76398f9310 foreign key (_warehouse_id) references _warehouse DEFERRABLE INITIALLY DEFERRED;

alter table if exists _inventory_adjustment add constraint FK47473d4d9ad9a8f3110377d27478d6a8 foreign key (_adjusted_by_id) references _user DEFERRABLE INITIALLY DEFERRED;

alter table if exists _inventory_adjustment add constraint FKb65562f5ecbc0a90001d4bdf3b1f0c32 foreign key (_organization_id) references _organization DEFERRABLE INITIALLY DEFERRED;

alter table if exists _inventory_adjustment_line add constraint FKfe0599a80ecb5c874bf022c9da18f566 foreign key (_product_id) references _product DEFERRABLE INITIALLY DEFERRED;

alter table if exists _inventory_adjustment_line add constraint FK77ccad60baf0cb451f142e05a5f1bcab foreign key (_inventory_adjustment_id) references _inventory_adjustment DEFERRABLE INITIALLY DEFERRED;

alter table if exists _inventory_movement add constraint FK76621e71e30185dd8e97b80686a856e7 foreign key (_warehouse_id) references _warehouse DEFERRABLE INITIALLY DEFERRED;

alter table if exists _inventory_movement add constraint FKe308a663eb7944439f599396f64c0a18 foreign key (_product_id) references _product DEFERRABLE INITIALLY DEFERRED;

alter table if exists _inventory_movement add constraint FKe606df8a0c0b8c52b952e0c497f534a3 foreign key (_performed_by_id) references _user DEFERRABLE INITIALLY DEFERRED;

alter table if exists _inventory_movement add constraint FKe1a0e76e3dc067b1ec6ca34147f04d25 foreign key (_organization_id) references _organization DEFERRABLE INITIALLY DEFERRED;

alter table if exists _notification_template add constraint FK53f05cd696746005b72a84d5f9ab9553 foreign key (_organization_id) references _organization DEFERRABLE INITIALLY DEFERRED;

alter table if exists _one_time_password add constraint FKd85dc405a5145f1d14e1f920c7ad1330 foreign key (_user_id) references _base_user DEFERRABLE INITIALLY DEFERRED;

alter table if exists _organization add constraint FKea779ceff1f22d5e5b6a9ae4be97941c foreign key (_logo_id) references _dfile DEFERRABLE INITIALLY DEFERRED;

alter table if exists _organization add constraint FK5e6d07a2515219bf4595efb491a8cc7f foreign key (_created_by_id) references _user DEFERRABLE INITIALLY DEFERRED;

alter table if exists _product add constraint FKf3778002b00bb440fa70462932729ab8 foreign key (_category_id) references _product_category DEFERRABLE INITIALLY DEFERRED;

alter table if exists _product add constraint FK0056c8312eb3bf85d81096cd19aeeafb foreign key (_base_uom_id) references _unit_of_measure DEFERRABLE INITIALLY DEFERRED;

alter table if exists _product add constraint FK5d028d86ad80c6f92ad16f1b013cdb0b foreign key (_image_id) references _dfile DEFERRABLE INITIALLY DEFERRED;

alter table if exists _product add constraint FK99ed7b9d70ac086a6f8d9f7622aaa353 foreign key (_organization_id) references _organization DEFERRABLE INITIALLY DEFERRED;

alter table if exists _product_category add constraint FK6fc95aacebf883c2869e990b1f26ebbf foreign key (_parent_category_id) references _product_category DEFERRABLE INITIALLY DEFERRED;

alter table if exists _product_category add constraint FK75ee7c755f792b9c80ee8c08ad1dd99b foreign key (_organization_id) references _organization DEFERRABLE INITIALLY DEFERRED;

alter table if exists _purchase_order add constraint FK11e8945d84e3885ffe88e56aa0c9fb64 foreign key (_vendor_id) references _vendor DEFERRABLE INITIALLY DEFERRED;

alter table if exists _purchase_order add constraint FKde47293281614176b38efcb1e1499bbf foreign key (_warehouse_id) references _warehouse DEFERRABLE INITIALLY DEFERRED;

alter table if exists _purchase_order add constraint FK378c46a36a0a324cb6b0495c09ebbb2e foreign key (_created_by_id) references _user DEFERRABLE INITIALLY DEFERRED;

alter table if exists _purchase_order add constraint FK6cc45c4c397e4ab4a4c7a46feea6ad12 foreign key (_approved_by_id) references _user DEFERRABLE INITIALLY DEFERRED;

alter table if exists _purchase_order add constraint FK65591d8de4835d63b4280f195aea6ed6 foreign key (_organization_id) references _organization DEFERRABLE INITIALLY DEFERRED;

alter table if exists _purchase_order_line add constraint FK81ddfbd9b9aeb48b47c17efda4169ff9 foreign key (_product_id) references _product DEFERRABLE INITIALLY DEFERRED;

alter table if exists _purchase_order_line add constraint FKd84186eeefc9eb2350dea38dc95ef65d foreign key (_uom_id) references _unit_of_measure DEFERRABLE INITIALLY DEFERRED;

alter table if exists _purchase_order_line add constraint FKab0cf515a01b2b6ad243ab46b8afc247 foreign key (_purchase_order_id) references _purchase_order DEFERRABLE INITIALLY DEFERRED;

alter table if exists _push_notification_users_fab88b add constraint FKd59e24c7c604408f73c61d3183d68bb0 foreign key (_users_id) references _base_user DEFERRABLE INITIALLY DEFERRED;
alter table if exists _push_notification_users_fab88b add constraint FKb3555c9a13e6d934de351bb7c2074624 foreign key (_push_notification_id) references _push_notification DEFERRABLE INITIALLY DEFERRED;

alter table if exists _push_notification_failed_devices_d63aa4 add constraint FK32b014e0dc9491455e5fa4b3e5bf98db foreign key (_failed_devices_id) references _user_device DEFERRABLE INITIALLY DEFERRED;
alter table if exists _push_notification_failed_devices_d63aa4 add constraint FKf21b4170f35b0508b9ef670d69186a80 foreign key (_push_notification_id) references _push_notification DEFERRABLE INITIALLY DEFERRED;

alter table if exists _report_config_values_a912b7 add constraint FKf63b8ce4f8377eeedcf87d62bd06f43c foreign key (_values_id) references _report_config_option DEFERRABLE INITIALLY DEFERRED;
alter table if exists _report_config_values_a912b7 add constraint FKc75f7ac5ec68527db571109ace37d022 foreign key (_report_config_id) references _report_config DEFERRABLE INITIALLY DEFERRED;

alter table if exists _sales_order add constraint FK53f80a3138507180dd945c5438f5e8a2 foreign key (_store_id) references _store DEFERRABLE INITIALLY DEFERRED;

alter table if exists _sales_order add constraint FKc764112d794e5cdc83ac1754a798adfd foreign key (_warehouse_id) references _warehouse DEFERRABLE INITIALLY DEFERRED;

alter table if exists _sales_order add constraint FK3569aeb95318abc3c620b8ad37e53f24 foreign key (_sold_by_id) references _user DEFERRABLE INITIALLY DEFERRED;

alter table if exists _sales_order add constraint FK1fe1860ac9a00232c584424159d913fd foreign key (_organization_id) references _organization DEFERRABLE INITIALLY DEFERRED;

alter table if exists _sales_order_line add constraint FK48695d24e0be60d2abdd1ba0e66228e5 foreign key (_product_id) references _product DEFERRABLE INITIALLY DEFERRED;

alter table if exists _sales_order_line add constraint FK5f0edf525025c139c8e9dc93e2bd6e55 foreign key (_sales_order_id) references _sales_order DEFERRABLE INITIALLY DEFERRED;

alter table if exists _sales_return add constraint FKee4e372063c68bd27985789c1d7eaded foreign key (_sales_order_id) references _sales_order DEFERRABLE INITIALLY DEFERRED;

alter table if exists _sales_return add constraint FK9a832643ae71e14cb905f4e46026e820 foreign key (_store_id) references _store DEFERRABLE INITIALLY DEFERRED;

alter table if exists _sales_return add constraint FK8f83c02542d879491fd32c5c79ab5a18 foreign key (_warehouse_id) references _warehouse DEFERRABLE INITIALLY DEFERRED;

alter table if exists _sales_return add constraint FK8cc040a6d9a34c121c3740b7b98ea0a9 foreign key (_processed_by_id) references _user DEFERRABLE INITIALLY DEFERRED;

alter table if exists _sales_return add constraint FK3775f882e376d529530220029acdc014 foreign key (_organization_id) references _organization DEFERRABLE INITIALLY DEFERRED;

alter table if exists _sales_return_line add constraint FKf34359ce1a42f7060d3364ff4c975ce0 foreign key (_product_id) references _product DEFERRABLE INITIALLY DEFERRED;

alter table if exists _sales_return_line add constraint FK400b2ffd436b591101c64fc54487e091 foreign key (_sales_return_id) references _sales_return DEFERRABLE INITIALLY DEFERRED;

alter table if exists _stock_alert add constraint FK22ee49da392823844ef544fb80954394 foreign key (_warehouse_id) references _warehouse DEFERRABLE INITIALLY DEFERRED;

alter table if exists _stock_alert add constraint FK2ca783c511d3ab34f5ff0109d8035ba3 foreign key (_product_id) references _product DEFERRABLE INITIALLY DEFERRED;

alter table if exists _stock_alert add constraint FKd13108b21f9fc91a19eae8cb1a4c57dd foreign key (_acknowledged_by_id) references _user DEFERRABLE INITIALLY DEFERRED;

alter table if exists _stock_alert add constraint FK3007bf7ec781a197b69e0f4031c59355 foreign key (_organization_id) references _organization DEFERRABLE INITIALLY DEFERRED;

alter table if exists _stock_batch add constraint FK1e1704b1dd1fb9f71eed2ef2171c4f55 foreign key (_product_id) references _product DEFERRABLE INITIALLY DEFERRED;

alter table if exists _stock_batch add constraint FK69222ff68256695875c5df5cac50e7ec foreign key (_warehouse_id) references _warehouse DEFERRABLE INITIALLY DEFERRED;

alter table if exists _stock_batch add constraint FK7abdb3c7254008e3b6822c11376e181b foreign key (_organization_id) references _organization DEFERRABLE INITIALLY DEFERRED;

alter table if exists _stock_transfer add constraint FKbca06e71cc8478a3115ad6c629c0621c foreign key (_source_warehouse_id) references _warehouse DEFERRABLE INITIALLY DEFERRED;

alter table if exists _stock_transfer add constraint FK248551de2b2a546d580e2dc7d1b62cb3 foreign key (_destination_warehouse_id) references _warehouse DEFERRABLE INITIALLY DEFERRED;

alter table if exists _stock_transfer add constraint FK6260debc70516906a4e021353d8b6c5b foreign key (_requested_by_id) references _user DEFERRABLE INITIALLY DEFERRED;

alter table if exists _stock_transfer add constraint FK4ebef8b540e8f2333268fae062f63922 foreign key (_approved_by_id) references _user DEFERRABLE INITIALLY DEFERRED;

alter table if exists _stock_transfer add constraint FKe3969a4f10bccfe9e25786bdccaa983b foreign key (_organization_id) references _organization DEFERRABLE INITIALLY DEFERRED;

alter table if exists _stock_transfer_line add constraint FK1097742d8db3bf29a44ce81e828d9aff foreign key (_product_id) references _product DEFERRABLE INITIALLY DEFERRED;

alter table if exists _stock_transfer_line add constraint FK9c258613536b787ee5220793e8fd7080 foreign key (_uom_id) references _unit_of_measure DEFERRABLE INITIALLY DEFERRED;

alter table if exists _stock_transfer_line add constraint FKaba0f6fad1540cd025123b742c4f875d foreign key (_stock_transfer_id) references _stock_transfer DEFERRABLE INITIALLY DEFERRED;

alter table if exists _store add constraint FK4ec21dcacca5a8c9b6f496d92ee911a3 foreign key (_manager_id) references _user DEFERRABLE INITIALLY DEFERRED;

alter table if exists _store add constraint FK355afeec33546238aeadf91fd04b3ee4 foreign key (_default_warehouse_id) references _warehouse DEFERRABLE INITIALLY DEFERRED;

alter table if exists _store add constraint FK0ddaa9d6ba2ee80b4d7e9abbe97c4167 foreign key (_organization_id) references _organization DEFERRABLE INITIALLY DEFERRED;

alter table if exists _supplier_contact add constraint FKd7ee1210c200bbfb89dd89d20c2b1f2a foreign key (_vendor_id) references _vendor DEFERRABLE INITIALLY DEFERRED;

alter table if exists _unit_of_measure add constraint FK36f4c20694d96ce83fba85474ea9188a foreign key (_organization_id) references _organization DEFERRABLE INITIALLY DEFERRED;

alter table if exists _user add constraint FK99bf34c07bf396f3cdf8e2bc19f40be8 foreign key (_organization_id) references _organization DEFERRABLE INITIALLY DEFERRED;

alter table if exists _user add constraint FK78ffd7f575d838e385b041cbd65bf081 foreign key (_id) references _base_user DEFERRABLE INITIALLY DEFERRED;

alter table if exists _user_device add constraint FKb91ffdcb09d66a567adef17f00fdaae4 foreign key (_user_id) references _base_user DEFERRABLE INITIALLY DEFERRED;

alter table if exists _user_invitation add constraint FK354ea24c599022e277b5bc2be5091e91 foreign key (_invited_by_id) references _user DEFERRABLE INITIALLY DEFERRED;

alter table if exists _user_invitation add constraint FKd09155b9b330ca1c86f8fc8c48c1b946 foreign key (_organization_id) references _organization DEFERRABLE INITIALLY DEFERRED;

alter table if exists _user_login_record add constraint FKe76e632d2524cecd0ce5f431cbb40376 foreign key (_user_id) references _base_user DEFERRABLE INITIALLY DEFERRED;

alter table if exists _user_profile add constraint FKd658b32f436cfb5a9aaeb44009c47392 foreign key (_user_id) references _user DEFERRABLE INITIALLY DEFERRED;

alter table if exists _user_profile add constraint FKe591437f293d7bbd03fe23e65358c752 foreign key (_avatar_id) references _dfile DEFERRABLE INITIALLY DEFERRED;

alter table if exists _user_profile_assigned_stores_dd1471 add constraint FK3ce00fd59d459a43326541bf41b2fbfa foreign key (_assigned_stores_id) references _store DEFERRABLE INITIALLY DEFERRED;
alter table if exists _user_profile_assigned_stores_dd1471 add constraint FKe60c863f96d6e694b3d4235540bef99d foreign key (_user_profile_id) references _user_profile DEFERRABLE INITIALLY DEFERRED;

alter table if exists _user_profile_assigned_warehouses_17ffb1 add constraint FK436b8bada9ef9dfcb803408f6ff20b09 foreign key (_assigned_warehouses_id) references _warehouse DEFERRABLE INITIALLY DEFERRED;
alter table if exists _user_profile_assigned_warehouses_17ffb1 add constraint FK88ee1955de20bec9557c96b5e75b6868 foreign key (_user_profile_id) references _user_profile DEFERRABLE INITIALLY DEFERRED;

alter table if exists _user_profile add constraint FK183852a5959641b70a010c65b682f93c foreign key (_organization_id) references _organization DEFERRABLE INITIALLY DEFERRED;

alter table if exists _user_role add constraint FKcdecc1ad641afbf12a5726bab9ac9ea8 foreign key (_organization_id) references _organization DEFERRABLE INITIALLY DEFERRED;

alter table if exists _vendor add constraint FK59b6d93942e6e9963922e8f6bb84a5e6 foreign key (_organization_id) references _organization DEFERRABLE INITIALLY DEFERRED;

alter table if exists _warehouse add constraint FKd123acf8cd91d7565376c3867ec99f8a foreign key (_store_id) references _store DEFERRABLE INITIALLY DEFERRED;

alter table if exists _warehouse add constraint FK3598afbf495ae70c88fc26afae0a189d foreign key (_organization_id) references _organization DEFERRABLE INITIALLY DEFERRED;

alter table if exists _warehouse_stock add constraint FKa6220f145ba1c75e7ccc114261b4e7a6 foreign key (_warehouse_id) references _warehouse DEFERRABLE INITIALLY DEFERRED;

alter table if exists _warehouse_stock add constraint FKe5bb73b79d5d200539cf8388f294fc7e foreign key (_product_id) references _product DEFERRABLE INITIALLY DEFERRED;

alter table if exists _warehouse_stock add constraint FK46692bb2cbc7d496c76a6955ed7176ae foreign key (_organization_id) references _organization DEFERRABLE INITIALLY DEFERRED;
