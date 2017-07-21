
var data_p={
		'f_bill_no':'201707201200028101',
		'f_doc_type':'ZO30',
		'f_mat_batch':'IKZ8BS100CX0170601040594320033',
		'f_bus_type':'MM2152',
		'userId':'EE6659E74C336777E040007F0100098B'
};


$.ajax({
	type : "post",
	url: cqt_prefix+'storage/saveBatchStorageOut',
	data:data_p,
	success : function(data) {}
});
