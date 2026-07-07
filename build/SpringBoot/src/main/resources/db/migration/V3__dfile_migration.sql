update _dfile a set _count = 0;
update _dfile a set _count = a._count + 1 from _organization b where a._id = b._logo_id;
update _dfile a set _count = a._count + 1 from _product b where a._id = b._image_id;
update _dfile a set _count = a._count + 1 from _user_profile b where a._id = b._avatar_id;