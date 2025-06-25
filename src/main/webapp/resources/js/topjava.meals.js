const mealAjaxUrl = "profile/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl
};

function filter() {
    $.ajax({
        type: "GET",
        data: $("#filter").serialize(),
        url: ctx.ajaxUrl + "filter",
        success: function (data) {
            console.log("callback data: " + JSON.stringify(data));
            ctx.datatableApi.clear().rows.add(data).draw();
        }
    });
}

$(function () {
    makeEditable(
        $("#mealsTable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        })
    );
});