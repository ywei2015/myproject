
var data_p={
		'f_bill_no':'201707171200028101',
		'f_doc_type':'ZO30',
		'f_mat_batch':'MSBLYA100LMB170701150000300031',
		'f_bus_type':'MM2152',
		'userId':'EE6659E74C336777E040007F0100098B'
};
$.ajax({
	type : "post",
	url: cqt_prefix+'storage/saveBatchStorageOut',
	data:data_p,
	success : function(data) {}
});

$.ajax({
	type : "post",
	url: cqt_prefix+'storage/saveBatchStorageOut',
	data:data_p,
	success : function(data) {}
});
