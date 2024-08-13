const toggler = document.querySelector(".btn");
toggler.addEventListener("click", function() {
	document.querySelector("#sidebar").classList.toggle("collapsed");
});

//users

$(document).ready(function() {
	$('#users').DataTable({
		pagingType: 'full_numbers',
		paging: true,
		pageLength: 10


	});
});

//shows

$(document).ready(function() {
	$('#shows').DataTable({
		pagingType: 'full_numbers',
		paging: true,
		pageLength: 10

	});
});