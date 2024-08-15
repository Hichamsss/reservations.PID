
//Programmes Datatable
$(document).ready(function() {
	var table = $('#programmeTable').DataTable({
		"paging": true,
		"pageLength": 5,
		"lengthChange": false
	});

	// Tri par titre
	$('#sortByOptions .sidebar-item a.sidebar-link').eq(0).on('click', function() {
		table.order([0, 'asc']).draw(); // Tri par la première colonne (Titre) en ordre croissant
	});

	// Tri par reservable: oui d'abord
	$('#sortByOptions .sidebar-item a.sidebar-link').eq(1).on('click', function() {
		table.order([1, 'asc']).order([1, 'desc'], ['oui']).draw(); // Tri par la deuxième colonne (Reservable) d'abord par 'oui'
	});

	// Tri par prix (ascendant)
	$('#sortByOptions .sidebar-item a.sidebar-link').eq(2).on('click', function() {
		table.order([2, 'asc']).draw(); // Tri par la troisième colonne (Prix) en ordre croissant
	});

	// Tri par prix (descendant)
	$('#sortByOptions .sidebar-item a.sidebar-link').eq(3).on('click', function() {
		table.order([2, 'desc']).draw(); // Tri par la troisième colonne (Prix) en ordre décroissant
	});

	// Tri par lieu
	$('#sortByOptions .sidebar-item a.sidebar-link').eq(4).on('click', function() {
		table.order([3, 'asc']).draw(); // Tri par la quatrième colonne (Lieu) en ordre croissant
	});

	// Tri par disponibilités
	$('#sortByOptions .sidebar-item a.sidebar-link').eq(5).on('click', function() {
		table.order([4, 'asc']).draw(); // Tri par la cinquième colonne (Disponibilités) en ordre croissant
	});
	
function filter() {
    var table = $('#programmeTable').DataTable();

    var filters = []; // Création d'une liste pour stocker tous les filtres

    // Récupération des valeurs des filtres
    var representationSelected = document.getElementById("representation").checked;
    var reservableSelected = document.getElementById("Reservable").checked;
    var below10Selected = document.getElementById("below10Checkbox").checked;
    var above10Selected = document.getElementById("above10Checkbox").checked;

    if (representationSelected) {
        filters.push('Representation');
    }

    if (reservableSelected) {
        filters.push('oui');
    }

    if (below10Selected) {
        filters.push('<10 €');
    } else if (above10Selected) {
        filters.push('>10 €');
    }

    var locationCheckboxes = document.querySelectorAll('.sidebar-item input[type="checkbox"]:checked');
    var locationFilters = Array.from(locationCheckboxes).map(checkbox => checkbox.nextElementSibling.textContent);
    filters = filters.concat(locationFilters); // Ajout des filtres de lieu à la liste

    var combinedFilter = filters.join('|'); // Construction d'un filtre global en combinant tous les filtres avec un OU

    // Appliquer le filtre global à chaque colonne
    table.columns().every(function () {
        this.search(combinedFilter, true, false); // Appliquer le filtre global sans rééchapper les valeurs
    });

    table.draw(); // Redessiner la table avec les filtres appliqués
}

// Appel de la fonction filter() lors du clic sur le bouton "Filtrer"
$('.btn-danger').on('click', function() {
    console.log("Bouton Filtrer cliqué!"); // Vérification du déclenchement de l'événement
    filter(); // Appel de la fonction filter()
});

});



//Profil/Reservation
$(document).ready(function () {
			$('#reservationTable').DataTable();
		});





