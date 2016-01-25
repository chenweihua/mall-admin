/**
 * 
 */

var table = null;

function initDataTable(listId, url, dataSrc, isSearch, searchText, data) {
	table = $('#' + listId)
			.DataTable(
					{
						ordering : false,
						processing : true,
						serverSide : true,
						searching : isSearch,
						bRetrieve: true,
						ajax : {
							url : url,
							data : data,
							dataSrc : dataSrc
						},
						sDom : "<'row'<'col-md-6'l><'col-md-6'f>r>t<'row'<'col-md-12'i><'col-md-12 center-block'p>>",
						sPaginationType : "bootstrap",
						oLanguage : {
							sLengthMenu : "_MENU_ 记录每页",
							sSearch : searchText,
							sZeroRecords : "暂无数据",
							sProcessing : "正在处理...",
							sEmptyTable : "暂无数据",
							sInfo : "_START_ - _END_ (共_TOTAL_条)",
							sInfoFiltered : "",
							oPaginate : {
								sFirst : "第一页",
								sLast : "最后一页",
								sNext : "下一页",
								sPrevious : "前一页",

							},

						}
					});
}

function del(url) {
	if (confirm("是否确定删除？")) {

		$.getJSON(url, function(ret) {
			if (ret.code != 0) {
				alert(ret.msg);
			} else {
				table.ajax.reload();
			}
		});
	}
}