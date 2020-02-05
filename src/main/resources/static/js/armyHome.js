var username = $("#username").val();
var factions = $(factions);
var armyArray = [];

$(document).ready(loadArmies());

$("#submitArmyButton").on("click", function (event) {
    event.preventDefault();
    $.ajax({
        method: "POST",
        url: "/api/addArmy/" + username,
        data: JSON.stringify({
            name: $("#armyName").val(),
            factionTypeId: parseInt($("#factionTypeId").val()),
            sizeClass: $("input[name=sizeClass]:checked").val(),
            notes: $("#armyNotes").val()
        }),
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        success: function () {
            window.location.replace("/home");
        },

        error: function () {
            alert("Error in adding a new army");
        }
    });
});


function deleteArmy(armyId) {
    $.ajax({
        method: "DELETE",
        url: `/api/deleteArmy/${username}/${armyId}`,
        success: function () {
            window.location.replace("/home");
            loadArmies();
        },

        error: function () {
            alert("Error in deleting army!");
        }
    });
}

function loadArmies() {
    $.ajax({
        method: "GET",
        url: `/api/armies/${username}`,
        success: function (armyList) {
            armyArray = armyList;
            armyList.forEach(army => {
                //make html for army div
                var id = army.armyId;
                var html = `<div class='card armyCard flipCard' id="${id}">`;

                //set faction picture for army
                var faction = army.factionTypeId;

                switch (faction) {
                    // case "Chaos Marines":
                    case 1:
                        html += `<div class="front">`
                        html += "<img class='card-img-top factionImg' src='http://bakadesign.dk/backoffice/wp-content/uploads/2017/12/chaos-star-01.svg'>";
                        html += `<i class="fas fa-pen editArmyIcon"></i>`;
                        html += `<div class="card-body">`;
                        html += `<h5 class="card-title">${army.name}</h5>`;
                        html += `<p>${army.sizeClass}</p>`;

                        if (army.notes != null) {
                            html += `<p class="card-text">${army.notes}</p>`
                        }
                        html += `<a href="/armybuilder/${id}" role="button" class="btn btn-success">Build!</a>`;
                        html += `<a href="#" role="button" class="btn btn-danger deleteArmyButton" id="deleteArmy" + ${id} onclick="deleteArmy(${id})">Delete</a>`;
                        html += `<input class="armyId" type="hidden" value=${id}>`
                        html += "</div>"; //close card body
                        html += "</div>"; //close front face
                        html += `<div class="back">`;
                        html += `<form id="editArmyForm">
    <div class="form-group">
        <input type="text" class="form-control" id="armyName${id}" name="name" placeholder="Army Name">
    </div>
    <div class="form-group">
        <select name="faction" id="factionTypeId${id}" class="form-control">
            <option selected="selected"> Faction </option>
            <option value="1">Chaos Space Marines</option>
            <option value="2">Chaos Demons</option>
            <option value="3">Drukhari</option>
            <option value="4">Craftworld Eldar</option>
            <option value="5">Eldar Harlequins</option>
            <option value="6">Necrons</option>
            <option value="7">Orks</option>
            <option value="8">T'au Empire</option>
            <option value="9">Tyranids</option>
            <option value="10">Genestealer Cults</option>
            <option value="11">Ultramarines</option>
            <option value="12">Blood Angels</option>
            <option value="13">Dark Angels</option>
            <option value="14">Grey Knights</option>
            <option value="15">Deathwatch</option>
            <option value="16">Space Wolves</option>
            <option value="17">Astra Militarum</option>
            <option value="18">Adepta Sororitas</option>
            <option value="19">Imperial Knights</option>
            <option value="20">Adeptus Mechanicus</option>
            <option value="21">Adeptus Custodes</option>
        </select>
    </div>

    <div class="form-group">
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeSmall${id}" value="small">
                <label class="form-check-label" for="armySizeSmall${id}">Small</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeMedium${id}" value="medium"
                    checked>
                <label class="form-check-label" for="armySizeMedium${id}">Medium</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeLarge${id}" value="large">
                <label class="form-check-label" for="armySizeLarge${id}">Large</label>
            </div>
        </div>
    <div class="form-group">
        <input type="text" name="armyNotes" class="form-control" id="armyNotes${id}" placeholder="Notes">
    </div>
    <a role="button" id="editArmyButton${id}" class="btn btn-success editArmyButton"><i class="fas fa-check"></i></a>
    <a role="button" class="btn btn-danger backFlipButton"><i class="fas fa-times"></i></a>
</form>`;
                        html += "</div>"; //close back face
                        html += "</div>"; //close card
                        break;
                    // case "Chaos Demons":
                    case 2:
                        html += `<div class="front">`
                        html += "<img class='card-img-top factionImg' src='http://bakadesign.dk/backoffice/wp-content/uploads/2017/12/chaos-star-01.svg'>";
                        html += `<i class="fas fa-pen editArmyIcon"></i>`;
                        html += `<div class="card-body">`;
                        html += `<h5 class="card-title">${army.name}</h5>`;
                        html += `<p>${army.sizeClass}</p>`;

                        if (army.notes != null) {
                            html += `<p class="card-text">${army.notes}</p>`
                        }
                        html += `<a href="/armybuilder/${id}" role="button" class="btn btn-success">Build!</a>`;
                        html += `<a href="#" role="button" class="btn btn-danger deleteArmyButton" id="deleteArmy" + ${id} onclick="deleteArmy(${id})">Delete</a>`;
                        html += `<input class="armyId" type="hidden" value=${id}>`
                        html += "</div>"; //close card body
                        html += "</div>"; //close front face
                        html += `<div class="back">`;
                        html += `<form id="editArmyForm">
    <div class="form-group">
        <input type="text" class="form-control" id="armyName${id}" name="name" placeholder="Army Name">
    </div>
    <div class="form-group">
        <select name="faction" id="factionTypeId${id}" class="form-control">
            <option selected="selected"> Faction </option>
            <option value="1">Chaos Space Marines</option>
            <option value="2">Chaos Demons</option>
            <option value="3">Drukhari</option>
            <option value="4">Craftworld Eldar</option>
            <option value="5">Eldar Harlequins</option>
            <option value="6">Necrons</option>
            <option value="7">Orks</option>
            <option value="8">T'au Empire</option>
            <option value="9">Tyranids</option>
            <option value="10">Genestealer Cults</option>
            <option value="11">Ultramarines</option>
            <option value="12">Blood Angels</option>
            <option value="13">Dark Angels</option>
            <option value="14">Grey Knights</option>
            <option value="15">Deathwatch</option>
            <option value="16">Space Wolves</option>
            <option value="17">Astra Militarum</option>
            <option value="18">Adepta Sororitas</option>
            <option value="19">Imperial Knights</option>
            <option value="20">Adeptus Mechanicus</option>
            <option value="21">Adeptus Custodes</option>
        </select>
    </div>

    <div class="form-group">
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeSmall${id}" value="small">
                <label class="form-check-label" for="armySizeSmall${id}">Small</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeMedium${id}" value="medium"
                    checked>
                <label class="form-check-label" for="armySizeMedium${id}">Medium</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeLarge${id}" value="large">
                <label class="form-check-label" for="armySizeLarge${id}">Large</label>
            </div>
        </div>
    <div class="form-group">
        <input type="text" name="armyNotes" class="form-control" id="armyNotes${id}" placeholder="Notes">
    </div>
    <a role="button" id="editArmyButton${id}" class="btn btn-success editArmyButton"><i class="fas fa-check"></i></a>
    <a role="button" class="btn btn-danger backFlipButton"><i class="fas fa-times"></i></a>
</form>`;
                        html += "</div>"; //close back face
                        html += "</div>"; //close card
                        break;
                    // case "Drukhari":
                    case 3:
                        html += `<div class="front">`
                        html += "<img class='card-img-top factionImg' src='http://bakadesign.dk/backoffice/wp-content/uploads/2019/04/Dark-Eldar.svg'>";
                        html += `<i class="fas fa-pen editArmyIcon"></i>`;
                        html += `<div class="card-body">`;
                        html += `<h5 class="card-title">${army.name}</h5>`;
                        html += `<p>${army.sizeClass}</p>`;

                        if (army.notes != null) {
                            html += `<p class="card-text">${army.notes}</p>`
                        }
                        html += `<a href="/armybuilder/${id}" role="button" class="btn btn-success">Build!</a>`;
                        html += `<a href="#" role="button" class="btn btn-danger deleteArmyButton" id="deleteArmy" + ${id} onclick="deleteArmy(${id})">Delete</a>`;
                        html += `<input class="armyId" type="hidden" value=${id}>`
                        html += "</div>"; //close card body
                        html += "</div>"; //close front face
                        html += `<div class="back">`;
                        html += `<form id="editArmyForm">
    <div class="form-group">
        <input type="text" class="form-control" id="armyName${id}" name="name" placeholder="Army Name">
    </div>
    <div class="form-group">
        <select name="faction" id="factionTypeId${id}" class="form-control">
            <option selected="selected"> Faction </option>
            <option value="1">Chaos Space Marines</option>
            <option value="2">Chaos Demons</option>
            <option value="3">Drukhari</option>
            <option value="4">Craftworld Eldar</option>
            <option value="5">Eldar Harlequins</option>
            <option value="6">Necrons</option>
            <option value="7">Orks</option>
            <option value="8">T'au Empire</option>
            <option value="9">Tyranids</option>
            <option value="10">Genestealer Cults</option>
            <option value="11">Ultramarines</option>
            <option value="12">Blood Angels</option>
            <option value="13">Dark Angels</option>
            <option value="14">Grey Knights</option>
            <option value="15">Deathwatch</option>
            <option value="16">Space Wolves</option>
            <option value="17">Astra Militarum</option>
            <option value="18">Adepta Sororitas</option>
            <option value="19">Imperial Knights</option>
            <option value="20">Adeptus Mechanicus</option>
            <option value="21">Adeptus Custodes</option>
        </select>
    </div>

    <div class="form-group">
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeSmall${id}" value="small">
                <label class="form-check-label" for="armySizeSmall${id}">Small</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeMedium${id}" value="medium"
                    checked>
                <label class="form-check-label" for="armySizeMedium${id}">Medium</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeLarge${id}" value="large">
                <label class="form-check-label" for="armySizeLarge${id}">Large</label>
            </div>
        </div>
    <div class="form-group">
        <input type="text" name="armyNotes" class="form-control" id="armyNotes${id}" placeholder="Notes">
    </div>
    <a role="button" id="editArmyButton${id}" class="btn btn-success editArmyButton"><i class="fas fa-check"></i></a>
    <a role="button" class="btn btn-danger backFlipButton"><i class="fas fa-times"></i></a>
</form>`;
                        html += "</div>"; //close back face
                        html += "</div>"; //close card
                        break;
                    // case "Craftworld Eldar":
                    case 4:
                        html += `<div class="front">`
                        html += "<img class='card-img-top factionImg' src='http://bakadesign.dk/backoffice/wp-content/uploads/2019/04/Dark-Eldar.svg'>";
                        html += `<i class="fas fa-pen editArmyIcon"></i>`;
                        html += `<div class="card-body">`;
                        html += `<h5 class="card-title">${army.name}</h5>`;
                        html += `<p>${army.sizeClass}</p>`;

                        if (army.notes != null) {
                            html += `<p class="card-text">${army.notes}</p>`
                        }
                        html += `<a href="/armybuilder/${id}" role="button" class="btn btn-success">Build!</a>`;
                        html += `<a href="#" role="button" class="btn btn-danger deleteArmyButton" id="deleteArmy" + ${id} onclick="deleteArmy(${id})">Delete</a>`;
                        html += `<input class="armyId" type="hidden" value=${id}>`
                        html += "</div>"; //close card body
                        html += "</div>"; //close front face
                        html += `<div class="back">`;
                        html += `<form id="editArmyForm">
    <div class="form-group">
        <input type="text" class="form-control" id="armyName${id}" name="name" placeholder="Army Name">
    </div>
    <div class="form-group">
        <select name="faction" id="factionTypeId${id}" class="form-control">
            <option selected="selected"> Faction </option>
            <option value="1">Chaos Space Marines</option>
            <option value="2">Chaos Demons</option>
            <option value="3">Drukhari</option>
            <option value="4">Craftworld Eldar</option>
            <option value="5">Eldar Harlequins</option>
            <option value="6">Necrons</option>
            <option value="7">Orks</option>
            <option value="8">T'au Empire</option>
            <option value="9">Tyranids</option>
            <option value="10">Genestealer Cults</option>
            <option value="11">Ultramarines</option>
            <option value="12">Blood Angels</option>
            <option value="13">Dark Angels</option>
            <option value="14">Grey Knights</option>
            <option value="15">Deathwatch</option>
            <option value="16">Space Wolves</option>
            <option value="17">Astra Militarum</option>
            <option value="18">Adepta Sororitas</option>
            <option value="19">Imperial Knights</option>
            <option value="20">Adeptus Mechanicus</option>
            <option value="21">Adeptus Custodes</option>
        </select>
    </div>

    <div class="form-group">
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeSmall${id}" value="small">
                <label class="form-check-label" for="armySizeSmall${id}">Small</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeMedium${id}" value="medium"
                    checked>
                <label class="form-check-label" for="armySizeMedium${id}">Medium</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeLarge${id}" value="large">
                <label class="form-check-label" for="armySizeLarge${id}">Large</label>
            </div>
        </div>
    <div class="form-group">
        <input type="text" name="armyNotes" class="form-control" id="armyNotes${id}" placeholder="Notes">
    </div>
    <a role="button" id="editArmyButton${id}" class="btn btn-success editArmyButton"><i class="fas fa-check"></i></a>
    <a role="button" class="btn btn-danger backFlipButton"><i class="fas fa-times"></i></a>
</form>`;
                        html += "</div>"; //close back face
                        html += "</div>"; //close card
                        break;
                    // case "Harlequins":
                    case 5:
                        html += `<div class="front">`
                        html += "<img class='card-img-top factionImg' src='http://bakadesign.dk/backoffice/wp-content/uploads/2019/04/Dark-Eldar.svg'>";
                        html += `<i class="fas fa-pen editArmyIcon"></i>`;
                        html += `<div class="card-body">`;
                        html += `<h5 class="card-title">${army.name}</h5>`;
                        html += `<p>${army.sizeClass}</p>`;

                        if (army.notes != null) {
                            html += `<p class="card-text">${army.notes}</p>`
                        }
                        html += `<a href="/armybuilder/${id}" role="button" class="btn btn-success">Build!</a>`;
                        html += `<a href="#" role="button" class="btn btn-danger deleteArmyButton" id="deleteArmy" + ${id} onclick="deleteArmy(${id})">Delete</a>`;
                        html += `<input class="armyId" type="hidden" value=${id}>`
                        html += "</div>"; //close card body
                        html += "</div>"; //close front face
                        html += `<div class="back">`;
                        html += `<form id="editArmyForm">
    <div class="form-group">
        <input type="text" class="form-control" id="armyName${id}" name="name" placeholder="Army Name">
    </div>
    <div class="form-group">
        <select name="faction" id="factionTypeId${id}" class="form-control">
            <option selected="selected"> Faction </option>
            <option value="1">Chaos Space Marines</option>
            <option value="2">Chaos Demons</option>
            <option value="3">Drukhari</option>
            <option value="4">Craftworld Eldar</option>
            <option value="5">Eldar Harlequins</option>
            <option value="6">Necrons</option>
            <option value="7">Orks</option>
            <option value="8">T'au Empire</option>
            <option value="9">Tyranids</option>
            <option value="10">Genestealer Cults</option>
            <option value="11">Ultramarines</option>
            <option value="12">Blood Angels</option>
            <option value="13">Dark Angels</option>
            <option value="14">Grey Knights</option>
            <option value="15">Deathwatch</option>
            <option value="16">Space Wolves</option>
            <option value="17">Astra Militarum</option>
            <option value="18">Adepta Sororitas</option>
            <option value="19">Imperial Knights</option>
            <option value="20">Adeptus Mechanicus</option>
            <option value="21">Adeptus Custodes</option>
        </select>
    </div>

    <div class="form-group">
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeSmall${id}" value="small">
                <label class="form-check-label" for="armySizeSmall${id}">Small</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeMedium${id}" value="medium"
                    checked>
                <label class="form-check-label" for="armySizeMedium${id}">Medium</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeLarge${id}" value="large">
                <label class="form-check-label" for="armySizeLarge${id}">Large</label>
            </div>
        </div>
    <div class="form-group">
        <input type="text" name="armyNotes" class="form-control" id="armyNotes${id}" placeholder="Notes">
    </div>
    <a role="button" id="editArmyButton${id}" class="btn btn-success editArmyButton"><i class="fas fa-check"></i></a>
    <a role="button" class="btn btn-danger backFlipButton"><i class="fas fa-times"></i></a>
</form>`;
                        html += "</div>"; //close back face
                        html += "</div>"; //close card
                        break;
                    // case "Necrons":
                    case 6:
                        html += `<div class="front">`
                        html += "<img class='card-img-top factionImg' src='http://bakadesign.dk/backoffice/wp-content/uploads/2018/11/Necrons.svg'>";
                        html += `<i class="fas fa-pen editArmyIcon"></i>`;
                        html += `<div class="card-body">`;
                        html += `<h5 class="card-title">${army.name}</h5>`;
                        html += `<p>${army.sizeClass}</p>`;

                        if (army.notes != null) {
                            html += `<p class="card-text">${army.notes}</p>`
                        }
                        html += `<a href="/armybuilder/${id}" role="button" class="btn btn-success">Build!</a>`;
                        html += `<a href="#" role="button" class="btn btn-danger deleteArmyButton" id="deleteArmy" + ${id} onclick="deleteArmy(${id})">Delete</a>`;
                        html += `<input class="armyId" type="hidden" value=${id}>`
                        html += "</div>"; //close card body
                        html += "</div>"; //close front face
                        html += `<div class="back">`;
                        html += `<form id="editArmyForm">
    <div class="form-group">
        <input type="text" class="form-control" id="armyName${id}" name="name" placeholder="Army Name">
    </div>
    <div class="form-group">
        <select name="faction" id="factionTypeId${id}" class="form-control">
            <option selected="selected"> Faction </option>
            <option value="1">Chaos Space Marines</option>
            <option value="2">Chaos Demons</option>
            <option value="3">Drukhari</option>
            <option value="4">Craftworld Eldar</option>
            <option value="5">Eldar Harlequins</option>
            <option value="6">Necrons</option>
            <option value="7">Orks</option>
            <option value="8">T'au Empire</option>
            <option value="9">Tyranids</option>
            <option value="10">Genestealer Cults</option>
            <option value="11">Ultramarines</option>
            <option value="12">Blood Angels</option>
            <option value="13">Dark Angels</option>
            <option value="14">Grey Knights</option>
            <option value="15">Deathwatch</option>
            <option value="16">Space Wolves</option>
            <option value="17">Astra Militarum</option>
            <option value="18">Adepta Sororitas</option>
            <option value="19">Imperial Knights</option>
            <option value="20">Adeptus Mechanicus</option>
            <option value="21">Adeptus Custodes</option>
        </select>
    </div>

    <div class="form-group">
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeSmall${id}" value="small">
                <label class="form-check-label" for="armySizeSmall${id}">Small</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeMedium${id}" value="medium"
                    checked>
                <label class="form-check-label" for="armySizeMedium${id}">Medium</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeLarge${id}" value="large">
                <label class="form-check-label" for="armySizeLarge${id}">Large</label>
            </div>
        </div>
    <div class="form-group">
        <input type="text" name="armyNotes" class="form-control" id="armyNotes${id}" placeholder="Notes">
    </div>
    <a role="button" id="editArmyButton${id}" class="btn btn-success editArmyButton"><i class="fas fa-check"></i></a>
    <a role="button" class="btn btn-danger backFlipButton"><i class="fas fa-times"></i></a>
</form>`;
                        html += "</div>"; //close back face
                        html += "</div>"; //close card
                        break;
                    // case "Orks":
                    case 7:
                        html += `<div class="front">`
                        html += "<img class='card-img-top factionImg' src='http://bakadesign.dk/backoffice/wp-content/uploads/2018/11/Orks.svg'>";
                        html += `<i class="fas fa-pen editArmyIcon"></i>`;
                        html += `<div class="card-body">`;
                        html += `<h5 class="card-title">${army.name}</h5>`;
                        html += `<p>${army.sizeClass}</p>`;

                        if (army.notes != null) {
                            html += `<p class="card-text">${army.notes}</p>`
                        }
                        html += `<a href="/armybuilder/${id}" role="button" class="btn btn-success">Build!</a>`;
                        html += `<a href="#" role="button" class="btn btn-danger deleteArmyButton" id="deleteArmy" + ${id} onclick="deleteArmy(${id})">Delete</a>`;
                        html += `<input class="armyId" type="hidden" value=${id}>`
                        html += "</div>"; //close card body
                        html += "</div>"; //close front face
                        html += `<div class="back">`;
                        html += `<form id="editArmyForm">
    <div class="form-group">
        <input type="text" class="form-control" id="armyName${id}" name="name" placeholder="Army Name">
    </div>
    <div class="form-group">
        <select name="faction" id="factionTypeId${id}" class="form-control">
            <option selected="selected"> Faction </option>
            <option value="1">Chaos Space Marines</option>
            <option value="2">Chaos Demons</option>
            <option value="3">Drukhari</option>
            <option value="4">Craftworld Eldar</option>
            <option value="5">Eldar Harlequins</option>
            <option value="6">Necrons</option>
            <option value="7">Orks</option>
            <option value="8">T'au Empire</option>
            <option value="9">Tyranids</option>
            <option value="10">Genestealer Cults</option>
            <option value="11">Ultramarines</option>
            <option value="12">Blood Angels</option>
            <option value="13">Dark Angels</option>
            <option value="14">Grey Knights</option>
            <option value="15">Deathwatch</option>
            <option value="16">Space Wolves</option>
            <option value="17">Astra Militarum</option>
            <option value="18">Adepta Sororitas</option>
            <option value="19">Imperial Knights</option>
            <option value="20">Adeptus Mechanicus</option>
            <option value="21">Adeptus Custodes</option>
        </select>
    </div>

    <div class="form-group">
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeSmall${id}" value="small">
                <label class="form-check-label" for="armySizeSmall${id}">Small</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeMedium${id}" value="medium"
                    checked>
                <label class="form-check-label" for="armySizeMedium${id}">Medium</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeLarge${id}" value="large">
                <label class="form-check-label" for="armySizeLarge${id}">Large</label>
            </div>
        </div>
    <div class="form-group">
        <input type="text" name="armyNotes" class="form-control" id="armyNotes${id}" placeholder="Notes">
    </div>
    <a role="button" id="editArmyButton${id}" class="btn btn-success editArmyButton"><i class="fas fa-check"></i></a>
    <a role="button" class="btn btn-danger backFlipButton"><i class="fas fa-times"></i></a>
</form>`;
                        html += "</div>"; //close back face
                        html += "</div>"; //close card
                        break;
                    // case "T'au Empire":
                    case 8:
                        html += `<div class="front">`
                        html += "<img class='card-img-top factionImg' src='http://bakadesign.dk/backoffice/wp-content/uploads/2018/11/Tau.svg'>";
                        html += `<i class="fas fa-pen editArmyIcon"></i>`;
                        html += `<div class="card-body">`;
                        html += `<h5 class="card-title">${army.name}</h5>`;
                        html += `<p>${army.sizeClass}</p>`;

                        if (army.notes != null) {
                            html += `<p class="card-text">${army.notes}</p>`
                        }
                        html += `<a href="/armybuilder/${id}" role="button" class="btn btn-success">Build!</a>`;
                        html += `<a href="#" role="button" class="btn btn-danger deleteArmyButton" id="deleteArmy" + ${id} onclick="deleteArmy(${id})">Delete</a>`;
                        html += `<input class="armyId" type="hidden" value=${id}>`
                        html += "</div>"; //close card body
                        html += "</div>"; //close front face
                        html += `<div class="back">`;
                        html += `<form id="editArmyForm">
    <div class="form-group">
        <input type="text" class="form-control" id="armyName${id}" name="name" placeholder="Army Name">
    </div>
    <div class="form-group">
        <select name="faction" id="factionTypeId${id}" class="form-control">
            <option selected="selected"> Faction </option>
            <option value="1">Chaos Space Marines</option>
            <option value="2">Chaos Demons</option>
            <option value="3">Drukhari</option>
            <option value="4">Craftworld Eldar</option>
            <option value="5">Eldar Harlequins</option>
            <option value="6">Necrons</option>
            <option value="7">Orks</option>
            <option value="8">T'au Empire</option>
            <option value="9">Tyranids</option>
            <option value="10">Genestealer Cults</option>
            <option value="11">Ultramarines</option>
            <option value="12">Blood Angels</option>
            <option value="13">Dark Angels</option>
            <option value="14">Grey Knights</option>
            <option value="15">Deathwatch</option>
            <option value="16">Space Wolves</option>
            <option value="17">Astra Militarum</option>
            <option value="18">Adepta Sororitas</option>
            <option value="19">Imperial Knights</option>
            <option value="20">Adeptus Mechanicus</option>
            <option value="21">Adeptus Custodes</option>
        </select>
    </div>

    <div class="form-group">
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeSmall${id}" value="small">
                <label class="form-check-label" for="armySizeSmall${id}">Small</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeMedium${id}" value="medium"
                    checked>
                <label class="form-check-label" for="armySizeMedium${id}">Medium</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeLarge${id}" value="large">
                <label class="form-check-label" for="armySizeLarge${id}">Large</label>
            </div>
        </div>
    <div class="form-group">
        <input type="text" name="armyNotes" class="form-control" id="armyNotes${id}" placeholder="Notes">
    </div>
    <a role="button" id="editArmyButton${id}" class="btn btn-success editArmyButton"><i class="fas fa-check"></i></a>
    <a role="button" class="btn btn-danger backFlipButton"><i class="fas fa-times"></i></a>
</form>`;
                        html += "</div>"; //close back face
                        html += "</div>"; //close card
                        break;
                    // case "Tyranids":
                    case 9:
                        html += `<div class="front">`
                        html += "<img class='card-img-top factionImg' src='http://bakadesign.dk/backoffice/wp-content/uploads/2018/10/tyranid.svg'>";
                        html += `<i class="fas fa-pen editArmyIcon"></i>`;
                        html += `<div class="card-body">`;
                        html += `<h5 class="card-title">${army.name}</h5>`;
                        html += `<p>${army.sizeClass}</p>`;

                        if (army.notes != null) {
                            html += `<p class="card-text">${army.notes}</p>`
                        }
                        html += `<a href="/armybuilder/${id}" role="button" class="btn btn-success">Build!</a>`;
                        html += `<a href="#" role="button" class="btn btn-danger deleteArmyButton" id="deleteArmy" + ${id} onclick="deleteArmy(${id})">Delete</a>`;
                        html += `<input class="armyId" type="hidden" value=${id}>`
                        html += "</div>"; //close card body
                        html += "</div>"; //close front face
                        html += `<div class="back">`;
                        html += `<form id="editArmyForm">
    <div class="form-group">
        <input type="text" class="form-control" id="armyName${id}" name="name" placeholder="Army Name">
    </div>
    <div class="form-group">
        <select name="faction" id="factionTypeId${id}" class="form-control">
            <option selected="selected"> Faction </option>
            <option value="1">Chaos Space Marines</option>
            <option value="2">Chaos Demons</option>
            <option value="3">Drukhari</option>
            <option value="4">Craftworld Eldar</option>
            <option value="5">Eldar Harlequins</option>
            <option value="6">Necrons</option>
            <option value="7">Orks</option>
            <option value="8">T'au Empire</option>
            <option value="9">Tyranids</option>
            <option value="10">Genestealer Cults</option>
            <option value="11">Ultramarines</option>
            <option value="12">Blood Angels</option>
            <option value="13">Dark Angels</option>
            <option value="14">Grey Knights</option>
            <option value="15">Deathwatch</option>
            <option value="16">Space Wolves</option>
            <option value="17">Astra Militarum</option>
            <option value="18">Adepta Sororitas</option>
            <option value="19">Imperial Knights</option>
            <option value="20">Adeptus Mechanicus</option>
            <option value="21">Adeptus Custodes</option>
        </select>
    </div>

    <div class="form-group">
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeSmall${id}" value="small">
                <label class="form-check-label" for="armySizeSmall${id}">Small</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeMedium${id}" value="medium"
                    checked>
                <label class="form-check-label" for="armySizeMedium${id}">Medium</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeLarge${id}" value="large">
                <label class="form-check-label" for="armySizeLarge${id}">Large</label>
            </div>
        </div>
    <div class="form-group">
        <input type="text" name="armyNotes" class="form-control" id="armyNotes${id}" placeholder="Notes">
    </div>
    <a role="button" id="editArmyButton${id}" class="btn btn-success editArmyButton"><i class="fas fa-check"></i></a>
    <a role="button" class="btn btn-danger backFlipButton"><i class="fas fa-times"></i></a>
</form>`;
                        html += "</div>"; //close back face
                        html += "</div>"; //close card
                        break;
                    // case "Genestealers":
                    case 10:
                        html += `<div class="front">`;
                        html += "<img class='card-img-top factionImg' src='http://bakadesign.dk/backoffice/wp-content/uploads/2018/10/tyranid.svg'>";
                        html += `<i class="fas fa-pen editArmyIcon"></i>`;
                        html += `<div class="card-body">`;
                        html += `<h5 class="card-title">${army.name}</h5>`;
                        html += `<p>${army.sizeClass}</p>`;

                        if (army.notes != null) {
                            html += `<p class="card-text">${army.notes}</p>`
                        }
                        html += `<a href="/armybuilder/${id}" role="button" class="btn btn-success">Build!</a>`;
                        html += `<a href="#" role="button" class="btn btn-danger deleteArmyButton" id="deleteArmy" + ${id} onclick="deleteArmy(${id})">Delete</a>`;
                        html += `<input class="armyId" type="hidden" value=${id}>`
                        html += "</div>"; //close card body
                        html += "</div>"; //close front face
                        html += `<div class="back">`;
                        html += `<form id="editArmyForm">
    <div class="form-group">
        <input type="text" class="form-control" id="armyName${id}" name="name" placeholder="Army Name">
    </div>
    <div class="form-group">
        <select name="faction" id="factionTypeId${id}" class="form-control">
            <option selected="selected"> Faction </option>
            <option value="1">Chaos Space Marines</option>
            <option value="2">Chaos Demons</option>
            <option value="3">Drukhari</option>
            <option value="4">Craftworld Eldar</option>
            <option value="5">Eldar Harlequins</option>
            <option value="6">Necrons</option>
            <option value="7">Orks</option>
            <option value="8">T'au Empire</option>
            <option value="9">Tyranids</option>
            <option value="10">Genestealer Cults</option>
            <option value="11">Ultramarines</option>
            <option value="12">Blood Angels</option>
            <option value="13">Dark Angels</option>
            <option value="14">Grey Knights</option>
            <option value="15">Deathwatch</option>
            <option value="16">Space Wolves</option>
            <option value="17">Astra Militarum</option>
            <option value="18">Adepta Sororitas</option>
            <option value="19">Imperial Knights</option>
            <option value="20">Adeptus Mechanicus</option>
            <option value="21">Adeptus Custodes</option>
        </select>
    </div>

    <div class="form-group">
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeSmall${id}" value="small">
                <label class="form-check-label" for="armySizeSmall${id}">Small</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeMedium${id}" value="medium"
                    checked>
                <label class="form-check-label" for="armySizeMedium${id}">Medium</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeLarge${id}" value="large">
                <label class="form-check-label" for="armySizeLarge${id}">Large</label>
            </div>
        </div>
    <div class="form-group">
        <input type="text" name="armyNotes" class="form-control" id="armyNotes${id}" placeholder="Notes">
    </div>
    <a role="button" id="editArmyButton${id}" class="btn btn-success editArmyButton"><i class="fas fa-check"></i></a>
    <a role="button" class="btn btn-danger backFlipButton"><i class="fas fa-times"></i></a>
</form>`;
                        html += "</div>"; //close back face
                        html += "</div>"; //close card
                        break;
                    // case "Ultramarines":
                    case 11:
                        html += `<div class="front">`;
                        html += "<img class='card-img-top factionImg' src='http://bakadesign.dk/backoffice/wp-content/uploads/2017/12/ultramarines.svg'>";
                        html += `<i class="fas fa-pen editArmyIcon"></i>`;
                        html += `<div class="card-body">`;
                        html += `<h5 class="card-title">${army.name}</h5>`;
                        html += `<p>${army.sizeClass}</p>`;

                        if (army.notes != null) {
                            html += `<p class="card-text">${army.notes}</p>`
                        }
                        html += `<a href="/armybuilder/${id}" role="button" class="btn btn-success">Build!</a>`;
                        html += `<a href="#" role="button" class="btn btn-danger deleteArmyButton" id="deleteArmy" + ${id} onclick="deleteArmy(${id})">Delete</a>`;
                        html += `<input class="armyId" type="hidden" value=${id}>`
                        html += "</div>"; //close card body
                        html += "</div>"; //close front face
                        html += `<div class="back">`;
                        html += `<form id="editArmyForm">
    <div class="form-group">
        <input type="text" class="form-control" id="armyName${id}" name="name" placeholder="Army Name">
    </div>
    <div class="form-group">
        <select name="faction" id="factionTypeId${id}" class="form-control">
            <option selected="selected"> Faction </option>
            <option value="1">Chaos Space Marines</option>
            <option value="2">Chaos Demons</option>
            <option value="3">Drukhari</option>
            <option value="4">Craftworld Eldar</option>
            <option value="5">Eldar Harlequins</option>
            <option value="6">Necrons</option>
            <option value="7">Orks</option>
            <option value="8">T'au Empire</option>
            <option value="9">Tyranids</option>
            <option value="10">Genestealer Cults</option>
            <option value="11">Ultramarines</option>
            <option value="12">Blood Angels</option>
            <option value="13">Dark Angels</option>
            <option value="14">Grey Knights</option>
            <option value="15">Deathwatch</option>
            <option value="16">Space Wolves</option>
            <option value="17">Astra Militarum</option>
            <option value="18">Adepta Sororitas</option>
            <option value="19">Imperial Knights</option>
            <option value="20">Adeptus Mechanicus</option>
            <option value="21">Adeptus Custodes</option>
        </select>
    </div>

    <div class="form-group">
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeSmall${id}" value="small">
                <label class="form-check-label" for="armySizeSmall${id}">Small</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeMedium${id}" value="medium"
                    checked>
                <label class="form-check-label" for="armySizeMedium${id}">Medium</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeLarge${id}" value="large">
                <label class="form-check-label" for="armySizeLarge${id}">Large</label>
            </div>
        </div>
    <div class="form-group">
        <input type="text" name="armyNotes" class="form-control" id="armyNotes${id}" placeholder="Notes">
    </div>
    <a role="button" id="editArmyButton${id}" class="btn btn-success editArmyButton"><i class="fas fa-check"></i></a>
    <a role="button" class="btn btn-danger backFlipButton"><i class="fas fa-times"></i></a>
</form>`;
                        html += "</div>"; //close back face
                        html += "</div>"; //close card
                        break;
                    // case "Blood Angels":
                    case 12:
                        html += `<div class="front">`;
                        html += "<img class='card-img-top factionImg' src='http://bakadesign.dk/backoffice/wp-content/uploads/2017/12/blood-angels.svg'>";
                        html += `<i class="fas fa-pen editArmyIcon"></i>`;
                        html += `<div class="card-body">`;
                        html += `<h5 class="card-title">${army.name}</h5>`;
                        html += `<p>${army.sizeClass}</p>`;

                        if (army.notes != null) {
                            html += `<p class="card-text">${army.notes}</p>`
                        }
                        html += `<a href="/armybuilder/${id}" role="button" class="btn btn-success">Build!</a>`;
                        html += `<a href="#" role="button" class="btn btn-danger deleteArmyButton" id="deleteArmy" + ${id} onclick="deleteArmy(${id})">Delete</a>`;
                        html += `<input class="armyId" type="hidden" value=${id}>`
                        html += "</div>"; //close card body
                        html += "</div>"; //close front face
                        html += `<div class="back">`;
                        html += `<form id="editArmyForm">
    <div class="form-group">
        <input type="text" class="form-control" id="armyName${id}" name="name" placeholder="Army Name">
    </div>
    <div class="form-group">
        <select name="faction" id="factionTypeId${id}" class="form-control">
            <option selected="selected"> Faction </option>
            <option value="1">Chaos Space Marines</option>
            <option value="2">Chaos Demons</option>
            <option value="3">Drukhari</option>
            <option value="4">Craftworld Eldar</option>
            <option value="5">Eldar Harlequins</option>
            <option value="6">Necrons</option>
            <option value="7">Orks</option>
            <option value="8">T'au Empire</option>
            <option value="9">Tyranids</option>
            <option value="10">Genestealer Cults</option>
            <option value="11">Ultramarines</option>
            <option value="12">Blood Angels</option>
            <option value="13">Dark Angels</option>
            <option value="14">Grey Knights</option>
            <option value="15">Deathwatch</option>
            <option value="16">Space Wolves</option>
            <option value="17">Astra Militarum</option>
            <option value="18">Adepta Sororitas</option>
            <option value="19">Imperial Knights</option>
            <option value="20">Adeptus Mechanicus</option>
            <option value="21">Adeptus Custodes</option>
        </select>
    </div>

    <div class="form-group">
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeSmall${id}" value="small">
                <label class="form-check-label" for="armySizeSmall${id}">Small</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeMedium${id}" value="medium"
                    checked>
                <label class="form-check-label" for="armySizeMedium${id}">Medium</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeLarge${id}" value="large">
                <label class="form-check-label" for="armySizeLarge${id}">Large</label>
            </div>
        </div>
    <div class="form-group">
        <input type="text" name="armyNotes" class="form-control" id="armyNotes${id}" placeholder="Notes">
    </div>
    <a role="button" id="editArmyButton${id}" class="btn btn-success editArmyButton"><i class="fas fa-check"></i></a>
    <a role="button" class="btn btn-danger backFlipButton"><i class="fas fa-times"></i></a>
</form>`;
                        html += "</div>"; //close back face
                        html += "</div>"; //close card
                        break;
                    // case "Dark Angels":
                    case 13:
                        html += `<div class="front">`;
                        html += "<img class='card-img-top factionImg' src='http://bakadesign.dk/backoffice/wp-content/uploads/2017/12/dark-angels.svg'>";
                        html += `<i class="fas fa-pen editArmyIcon"></i>`;
                        html += `<div class="card-body">`;
                        html += `<h5 class="card-title">${army.name}</h5>`;
                        html += `<p>${army.sizeClass}</p>`;

                        if (army.notes != null) {
                            html += `<p class="card-text">${army.notes}</p>`
                        }
                        html += `<a href="/armybuilder/${id}" role="button" class="btn btn-success">Build!</a>`;
                        html += `<a href="#" role="button" class="btn btn-danger deleteArmyButton" id="deleteArmy" + ${id} onclick="deleteArmy(${id})">Delete</a>`;
                        html += `<input class="armyId" type="hidden" value=${id}>`
                        html += "</div>"; //close card body
                        html += "</div>"; //close front face
                        html += `<div class="back">`;
                        html += `<form id="editArmyForm">
    <div class="form-group">
        <input type="text" class="form-control" id="armyName${id}" name="name" placeholder="Army Name">
    </div>
    <div class="form-group">
        <select name="faction" id="factionTypeId${id}" class="form-control">
            <option selected="selected"> Faction </option>
            <option value="1">Chaos Space Marines</option>
            <option value="2">Chaos Demons</option>
            <option value="3">Drukhari</option>
            <option value="4">Craftworld Eldar</option>
            <option value="5">Eldar Harlequins</option>
            <option value="6">Necrons</option>
            <option value="7">Orks</option>
            <option value="8">T'au Empire</option>
            <option value="9">Tyranids</option>
            <option value="10">Genestealer Cults</option>
            <option value="11">Ultramarines</option>
            <option value="12">Blood Angels</option>
            <option value="13">Dark Angels</option>
            <option value="14">Grey Knights</option>
            <option value="15">Deathwatch</option>
            <option value="16">Space Wolves</option>
            <option value="17">Astra Militarum</option>
            <option value="18">Adepta Sororitas</option>
            <option value="19">Imperial Knights</option>
            <option value="20">Adeptus Mechanicus</option>
            <option value="21">Adeptus Custodes</option>
        </select>
    </div>

    <div class="form-group">
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeSmall${id}" value="small">
                <label class="form-check-label" for="armySizeSmall${id}">Small</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeMedium${id}" value="medium"
                    checked>
                <label class="form-check-label" for="armySizeMedium${id}">Medium</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeLarge${id}" value="large">
                <label class="form-check-label" for="armySizeLarge${id}">Large</label>
            </div>
        </div>
    <div class="form-group">
        <input type="text" name="armyNotes" class="form-control" id="armyNotes${id}" placeholder="Notes">
    </div>
    <a role="button" id="editArmyButton${id}" class="btn btn-success editArmyButton"><i class="fas fa-check"></i></a>
    <a role="button" class="btn btn-danger backFlipButton"><i class="fas fa-times"></i></a>
</form>`;
                        html += "</div>"; //close back face
                        html += "</div>"; //close card
                        break;
                    // case "Grey Knights":
                    case 14:
                        html += `<div class="front">`;
                        html += "<img class='card-img-top factionImg' src='http://bakadesign.dk/backoffice/wp-content/uploads/2018/11/Grey-knights.svg'>";
                        html += `<i class="fas fa-pen editArmyIcon"></i>`;
                        html += `<div class="card-body">`;
                        html += `<h5 class="card-title">${army.name}</h5>`;
                        html += `<p>${army.sizeClass}</p>`;

                        if (army.notes != null) {
                            html += `<p class="card-text">${army.notes}</p>`
                        }
                        html += `<a href="/armybuilder/${id}" role="button" class="btn btn-success">Build!</a>`;
                        html += `<a href="#" role="button" class="btn btn-danger deleteArmyButton" id="deleteArmy" + ${id} onclick="deleteArmy(${id})">Delete</a>`;
                        html += `<input class="armyId" type="hidden" value=${id}>`
                        html += "</div>"; //close card body
                        html += "</div>"; //close front face
                        html += `<div class="back">`;
                        html += `<form id="editArmyForm">
    <div class="form-group">
        <input type="text" class="form-control" id="armyName${id}" name="name" placeholder="Army Name">
    </div>
    <div class="form-group">
        <select name="faction" id="factionTypeId${id}" class="form-control">
            <option selected="selected"> Faction </option>
            <option value="1">Chaos Space Marines</option>
            <option value="2">Chaos Demons</option>
            <option value="3">Drukhari</option>
            <option value="4">Craftworld Eldar</option>
            <option value="5">Eldar Harlequins</option>
            <option value="6">Necrons</option>
            <option value="7">Orks</option>
            <option value="8">T'au Empire</option>
            <option value="9">Tyranids</option>
            <option value="10">Genestealer Cults</option>
            <option value="11">Ultramarines</option>
            <option value="12">Blood Angels</option>
            <option value="13">Dark Angels</option>
            <option value="14">Grey Knights</option>
            <option value="15">Deathwatch</option>
            <option value="16">Space Wolves</option>
            <option value="17">Astra Militarum</option>
            <option value="18">Adepta Sororitas</option>
            <option value="19">Imperial Knights</option>
            <option value="20">Adeptus Mechanicus</option>
            <option value="21">Adeptus Custodes</option>
        </select>
    </div>

    <div class="form-group">
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeSmall${id}" value="small">
                <label class="form-check-label" for="armySizeSmall${id}">Small</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeMedium${id}" value="medium"
                    checked>
                <label class="form-check-label" for="armySizeMedium${id}">Medium</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeLarge${id}" value="large">
                <label class="form-check-label" for="armySizeLarge${id}">Large</label>
            </div>
        </div>
    <div class="form-group">
        <input type="text" name="armyNotes" class="form-control" id="armyNotes${id}" placeholder="Notes">
    </div>
    <a role="button" id="editArmyButton${id}" class="btn btn-success editArmyButton"><i class="fas fa-check"></i></a>
    <a role="button" class="btn btn-danger backFlipButton"><i class="fas fa-times"></i></a>
</form>`;
                        html += "</div>"; //close back face
                        html += "</div>"; //close card
                        break;
                    // case "Deathwatch":
                    case 15:
                        html += `<div class="front">`;
                        html += "<img class='card-img-top factionImg' src='http://bakadesign.dk/backoffice/wp-content/uploads/2018/11/Deathwatch.svg'>";
                        html += `<i class="fas fa-pen editArmyIcon"></i>`;
                        html += `<div class="card-body">`;
                        html += `<h5 class="card-title">${army.name}</h5>`;
                        html += `<p>${army.sizeClass}</p>`;

                        if (army.notes != null) {
                            html += `<p class="card-text">${army.notes}</p>`
                        }
                        html += `<a href="/armybuilder/${id}" role="button" class="btn btn-success">Build!</a>`;
                        html += `<a href="#" role="button" class="btn btn-danger deleteArmyButton" id="deleteArmy" + ${id} onclick="deleteArmy(${id})">Delete</a>`;
                        html += `<input class="armyId" type="hidden" value=${id}>`
                        html += "</div>"; //close card body
                        html += "</div>"; //close front face
                        html += `<div class="back">`;
                        html += `<form id="editArmyForm">
    <div class="form-group">
        <input type="text" class="form-control" id="armyName${id}" name="name" placeholder="Army Name">
    </div>
    <div class="form-group">
        <select name="faction" id="factionTypeId${id}" class="form-control">
            <option selected="selected"> Faction </option>
            <option value="1">Chaos Space Marines</option>
            <option value="2">Chaos Demons</option>
            <option value="3">Drukhari</option>
            <option value="4">Craftworld Eldar</option>
            <option value="5">Eldar Harlequins</option>
            <option value="6">Necrons</option>
            <option value="7">Orks</option>
            <option value="8">T'au Empire</option>
            <option value="9">Tyranids</option>
            <option value="10">Genestealer Cults</option>
            <option value="11">Ultramarines</option>
            <option value="12">Blood Angels</option>
            <option value="13">Dark Angels</option>
            <option value="14">Grey Knights</option>
            <option value="15">Deathwatch</option>
            <option value="16">Space Wolves</option>
            <option value="17">Astra Militarum</option>
            <option value="18">Adepta Sororitas</option>
            <option value="19">Imperial Knights</option>
            <option value="20">Adeptus Mechanicus</option>
            <option value="21">Adeptus Custodes</option>
        </select>
    </div>

    <div class="form-group">
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeSmall${id}" value="small">
                <label class="form-check-label" for="armySizeSmall${id}">Small</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeMedium${id}" value="medium"
                    checked>
                <label class="form-check-label" for="armySizeMedium${id}">Medium</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeLarge${id}" value="large">
                <label class="form-check-label" for="armySizeLarge${id}">Large</label>
            </div>
        </div>
    <div class="form-group">
        <input type="text" name="armyNotes" class="form-control" id="armyNotes${id}" placeholder="Notes">
    </div>
    <a role="button" id="editArmyButton${id}" class="btn btn-success editArmyButton"><i class="fas fa-check"></i></a>
    <a role="button" class="btn btn-danger backFlipButton"><i class="fas fa-times"></i></a>
</form>`;
                        html += "</div>"; //close back face
                        html += "</div>"; //close card
                        break;
                    // case "Space Wolves":
                    case 16:
                        html += `<div class="front">`;
                        html += "<img class='card-img-top factionImg' src='http://bakadesign.dk/backoffice/wp-content/uploads/2017/12/space-wolfs.svg'>";
                        html += `<i class="fas fa-pen editArmyIcon"></i>`;
                        html += `<div class="card-body">`;
                        html += `<h5 class="card-title">${army.name}</h5>`;
                        html += `<p>${army.sizeClass}</p>`;

                        if (army.notes != null) {
                            html += `<p class="card-text">${army.notes}</p>`
                        }
                        html += `<a href="/armybuilder/${id}" role="button" class="btn btn-success">Build!</a>`;
                        html += `<a href="#" role="button" class="btn btn-danger deleteArmyButton" id="deleteArmy" + ${id} onclick="deleteArmy(${id})">Delete</a>`;
                        html += `<input class="armyId" type="hidden" value=${id}>`
                        html += "</div>"; //close card body
                        html += "</div>"; //close front face
                        html += `<div class="back">`;
                        html += `<form id="editArmyForm">
    <div class="form-group">
        <input type="text" class="form-control" id="armyName${id}" name="name" placeholder="Army Name">
    </div>
    <div class="form-group">
        <select name="faction" id="factionTypeId${id}" class="form-control">
            <option selected="selected"> Faction </option>
            <option value="1">Chaos Space Marines</option>
            <option value="2">Chaos Demons</option>
            <option value="3">Drukhari</option>
            <option value="4">Craftworld Eldar</option>
            <option value="5">Eldar Harlequins</option>
            <option value="6">Necrons</option>
            <option value="7">Orks</option>
            <option value="8">T'au Empire</option>
            <option value="9">Tyranids</option>
            <option value="10">Genestealer Cults</option>
            <option value="11">Ultramarines</option>
            <option value="12">Blood Angels</option>
            <option value="13">Dark Angels</option>
            <option value="14">Grey Knights</option>
            <option value="15">Deathwatch</option>
            <option value="16">Space Wolves</option>
            <option value="17">Astra Militarum</option>
            <option value="18">Adepta Sororitas</option>
            <option value="19">Imperial Knights</option>
            <option value="20">Adeptus Mechanicus</option>
            <option value="21">Adeptus Custodes</option>
        </select>
    </div>

    <div class="form-group">
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeSmall${id}" value="small">
                <label class="form-check-label" for="armySizeSmall${id}">Small</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeMedium${id}" value="medium"
                    checked>
                <label class="form-check-label" for="armySizeMedium${id}">Medium</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeLarge${id}" value="large">
                <label class="form-check-label" for="armySizeLarge${id}">Large</label>
            </div>
        </div>
    <div class="form-group">
        <input type="text" name="armyNotes" class="form-control" id="armyNotes${id}" placeholder="Notes">
    </div>
    <a role="button" id="editArmyButton${id}" class="btn btn-success editArmyButton"><i class="fas fa-check"></i></a>
    <a role="button" class="btn btn-danger backFlipButton"><i class="fas fa-times"></i></a>
</form>`;
                        html += "</div>"; //close back face
                        html += "</div>"; //close card
                        break;
                    // case "Astra Militarum":
                    case 17:
                        html += `<div class="front">`;
                        html += "<img class='card-img-top factionImg' src='http://bakadesign.dk/backoffice/wp-content/uploads/2017/12/Astra-Militarum.svg'>";
                        html += `<i class="fas fa-pen editArmyIcon"></i>`;
                        html += `<div class="card-body">`;
                        html += `<h5 class="card-title">${army.name}</h5>`;
                        html += `<p>${army.sizeClass}</p>`;

                        if (army.notes != null) {
                            html += `<p class="card-text">${army.notes}</p>`
                        }
                        html += `<a href="/armybuilder/${id}" role="button" class="btn btn-success">Build!</a>`;
                        html += `<a href="#" role="button" class="btn btn-danger deleteArmyButton" id="deleteArmy" + ${id} onclick="deleteArmy(${id})">Delete</a>`;
                        html += `<input class="armyId" type="hidden" value=${id}>`
                        html += "</div>"; //close card body
                        html += "</div>"; //close front face
                        html += `<div class="back">`;
                        html += `<form id="editArmyForm">
    <div class="form-group">
        <input type="text" class="form-control" id="armyName${id}" name="name" placeholder="Army Name">
    </div>
    <div class="form-group">
        <select name="faction" id="factionTypeId${id}" class="form-control">
            <option selected="selected"> Faction </option>
            <option value="1">Chaos Space Marines</option>
            <option value="2">Chaos Demons</option>
            <option value="3">Drukhari</option>
            <option value="4">Craftworld Eldar</option>
            <option value="5">Eldar Harlequins</option>
            <option value="6">Necrons</option>
            <option value="7">Orks</option>
            <option value="8">T'au Empire</option>
            <option value="9">Tyranids</option>
            <option value="10">Genestealer Cults</option>
            <option value="11">Ultramarines</option>
            <option value="12">Blood Angels</option>
            <option value="13">Dark Angels</option>
            <option value="14">Grey Knights</option>
            <option value="15">Deathwatch</option>
            <option value="16">Space Wolves</option>
            <option value="17">Astra Militarum</option>
            <option value="18">Adepta Sororitas</option>
            <option value="19">Imperial Knights</option>
            <option value="20">Adeptus Mechanicus</option>
            <option value="21">Adeptus Custodes</option>
        </select>
    </div>

    <div class="form-group">
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeSmall${id}" value="small">
                <label class="form-check-label" for="armySizeSmall${id}">Small</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeMedium${id}" value="medium"
                    checked>
                <label class="form-check-label" for="armySizeMedium${id}">Medium</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeLarge${id}" value="large">
                <label class="form-check-label" for="armySizeLarge${id}">Large</label>
            </div>
        </div>
    <div class="form-group">
        <input type="text" name="armyNotes" class="form-control" id="armyNotes${id}" placeholder="Notes">
    </div>
    <a role="button" id="editArmyButton${id}" class="btn btn-success editArmyButton"><i class="fas fa-check"></i></a>
    <a role="button" class="btn btn-danger backFlipButton"><i class="fas fa-times"></i></a>
</form>`;
                        html += "</div>"; //close back face
                        html += "</div>"; //close card
                        break;
                    // case "Adepta Sororitas":
                    case 18:
                        html += `<div class="front">`;
                        html += "<img class='card-img-top factionImg' src='http://bakadesign.dk/backoffice/wp-content/uploads/2017/12/sisters-of-battle.svg'>";
                        html += `<i class="fas fa-pen editArmyIcon"></i>`;
                        html += `<div class="card-body">`;
                        html += `<h5 class="card-title">${army.name}</h5>`;
                        html += `<p>${army.sizeClass}</p>`;

                        if (army.notes != null) {
                            html += `<p class="card-text">${army.notes}</p>`
                        }
                        html += `<a href="/armybuilder/${id}" role="button" class="btn btn-success">Build!</a>`;
                        html += `<a href="#" role="button" class="btn btn-danger deleteArmyButton" id="deleteArmy" + ${id} onclick="deleteArmy(${id})">Delete</a>`;
                        html += `<input class="armyId" type="hidden" value=${id}>`
                        html += "</div>"; //close card body
                        html += "</div>"; //close front face
                        html += `<div class="back">`;
                        html += `<form id="editArmyForm">
    <div class="form-group">
        <input type="text" class="form-control" id="armyName${id}" name="name" placeholder="Army Name">
    </div>
    <div class="form-group">
        <select name="faction" id="factionTypeId${id}" class="form-control">
            <option selected="selected"> Faction </option>
            <option value="1">Chaos Space Marines</option>
            <option value="2">Chaos Demons</option>
            <option value="3">Drukhari</option>
            <option value="4">Craftworld Eldar</option>
            <option value="5">Eldar Harlequins</option>
            <option value="6">Necrons</option>
            <option value="7">Orks</option>
            <option value="8">T'au Empire</option>
            <option value="9">Tyranids</option>
            <option value="10">Genestealer Cults</option>
            <option value="11">Ultramarines</option>
            <option value="12">Blood Angels</option>
            <option value="13">Dark Angels</option>
            <option value="14">Grey Knights</option>
            <option value="15">Deathwatch</option>
            <option value="16">Space Wolves</option>
            <option value="17">Astra Militarum</option>
            <option value="18">Adepta Sororitas</option>
            <option value="19">Imperial Knights</option>
            <option value="20">Adeptus Mechanicus</option>
            <option value="21">Adeptus Custodes</option>
        </select>
    </div>

    <div class="form-group">
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeSmall${id}" value="small">
                <label class="form-check-label" for="armySizeSmall${id}">Small</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeMedium${id}" value="medium"
                    checked>
                <label class="form-check-label" for="armySizeMedium${id}">Medium</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeLarge${id}" value="large">
                <label class="form-check-label" for="armySizeLarge${id}">Large</label>
            </div>
        </div>
    <div class="form-group">
        <input type="text" name="armyNotes" class="form-control" id="armyNotes${id}" placeholder="Notes">
    </div>
    <a role="button" id="editArmyButton${id}" class="btn btn-success editArmyButton"><i class="fas fa-check"></i></a>
    <a role="button" class="btn btn-danger backFlipButton"><i class="fas fa-times"></i></a>
</form>`;
                        html += "</div>"; //close back face
                        html += "</div>"; //close card
                        break;
                    // case "Imperial Knights":
                    case 19:
                        html += `<div class="front">`;
                        html += "<img class='card-img-top factionImg' src='http://bakadesign.dk/backoffice/wp-content/uploads/2017/12/imperial-aquila-02.svg'>";
                        html += `<i class="fas fa-pen editArmyIcon"></i>`;
                        html += `<div class="card-body">`;
                        html += `<h5 class="card-title">${army.name}</h5>`;
                        html += `<p>${army.sizeClass}</p>`;

                        if (army.notes != null) {
                            html += `<p class="card-text">${army.notes}</p>`
                        }
                        html += `<a href="/armybuilder/${id}" role="button" class="btn btn-success">Build!</a>`;
                        html += `<a href="#" role="button" class="btn btn-danger deleteArmyButton" id="deleteArmy" + ${id} onclick="deleteArmy(${id})">Delete</a>`;
                        html += `<input class="armyId" type="hidden" value=${id}>`
                        html += "</div>"; //close card body
                        html += "</div>"; //close front face
                        html += `<div class="back">`;
                        html += `<form id="editArmyForm">
    <div class="form-group">
        <input type="text" class="form-control" id="armyName${id}" name="name" placeholder="Army Name">
    </div>
    <div class="form-group">
        <select name="faction" id="factionTypeId${id}" class="form-control">
            <option selected="selected"> Faction </option>
            <option value="1">Chaos Space Marines</option>
            <option value="2">Chaos Demons</option>
            <option value="3">Drukhari</option>
            <option value="4">Craftworld Eldar</option>
            <option value="5">Eldar Harlequins</option>
            <option value="6">Necrons</option>
            <option value="7">Orks</option>
            <option value="8">T'au Empire</option>
            <option value="9">Tyranids</option>
            <option value="10">Genestealer Cults</option>
            <option value="11">Ultramarines</option>
            <option value="12">Blood Angels</option>
            <option value="13">Dark Angels</option>
            <option value="14">Grey Knights</option>
            <option value="15">Deathwatch</option>
            <option value="16">Space Wolves</option>
            <option value="17">Astra Militarum</option>
            <option value="18">Adepta Sororitas</option>
            <option value="19">Imperial Knights</option>
            <option value="20">Adeptus Mechanicus</option>
            <option value="21">Adeptus Custodes</option>
        </select>
    </div>

    <div class="form-group">
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeSmall${id}" value="small">
                <label class="form-check-label" for="armySizeSmall${id}">Small</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeMedium${id}" value="medium"
                    checked>
                <label class="form-check-label" for="armySizeMedium${id}">Medium</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeLarge${id}" value="large">
                <label class="form-check-label" for="armySizeLarge${id}">Large</label>
            </div>
        </div>
    <div class="form-group">
        <input type="text" name="armyNotes" class="form-control" id="armyNotes${id}" placeholder="Notes">
    </div>
    <a role="button" id="editArmyButton${id}" class="btn btn-success editArmyButton"><i class="fas fa-check"></i></a>
    <a role="button" class="btn btn-danger backFlipButton"><i class="fas fa-times"></i></a>
</form>`;
                        html += "</div>"; //close back face
                        html += "</div>"; //close card
                        break;
                    // case "Adeptus Mechanicus":
                    case 20:
                        html += `<div class="front">`;
                        html += "<img class='card-img-top factionImg' src='http://bakadesign.dk/backoffice/wp-content/uploads/2017/12/adeptus-mechanicus.svg'>";
                        html += `<i class="fas fa-pen editArmyIcon"></i>`;
                        html += `<div class="card-body">`;
                        html += `<h5 class="card-title">${army.name}</h5>`;
                        html += `<p>${army.sizeClass}</p>`;

                        if (army.notes != null) {
                            html += `<p class="card-text">${army.notes}</p>`
                        }
                        html += `<a href="/armybuilder/${id}" role="button" class="btn btn-success">Build!</a>`;
                        html += `<a href="#" role="button" class="btn btn-danger deleteArmyButton" id="deleteArmy" + ${id} onclick="deleteArmy(${id})">Delete</a>`;
                        html += `<input class="armyId" type="hidden" value=${id}>`
                        html += "</div>"; //close card body
                        html += "</div>"; //close front face
                        html += `<div class="back">`;
                        html += `<form id="editArmyForm">
    <div class="form-group">
        <input type="text" class="form-control" id="armyName${id}" name="name" placeholder="Army Name">
    </div>
    <div class="form-group">
        <select name="faction" id="factionTypeId${id}" class="form-control">
            <option selected="selected"> Faction </option>
            <option value="1">Chaos Space Marines</option>
            <option value="2">Chaos Demons</option>
            <option value="3">Drukhari</option>
            <option value="4">Craftworld Eldar</option>
            <option value="5">Eldar Harlequins</option>
            <option value="6">Necrons</option>
            <option value="7">Orks</option>
            <option value="8">T'au Empire</option>
            <option value="9">Tyranids</option>
            <option value="10">Genestealer Cults</option>
            <option value="11">Ultramarines</option>
            <option value="12">Blood Angels</option>
            <option value="13">Dark Angels</option>
            <option value="14">Grey Knights</option>
            <option value="15">Deathwatch</option>
            <option value="16">Space Wolves</option>
            <option value="17">Astra Militarum</option>
            <option value="18">Adepta Sororitas</option>
            <option value="19">Imperial Knights</option>
            <option value="20">Adeptus Mechanicus</option>
            <option value="21">Adeptus Custodes</option>
        </select>
    </div>

    <div class="form-group">
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeSmall${id}" value="small">
                <label class="form-check-label" for="armySizeSmall${id}">Small</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeMedium${id}" value="medium"
                    checked>
                <label class="form-check-label" for="armySizeMedium${id}">Medium</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeLarge${id}" value="large">
                <label class="form-check-label" for="armySizeLarge${id}">Large</label>
            </div>
        </div>
    <div class="form-group">
        <input type="text" name="armyNotes" class="form-control" id="armyNotes${id}" placeholder="Notes">
    </div>
    <a role="button" id="editArmyButton${id}" class="btn btn-success editArmyButton"><i class="fas fa-check"></i></a>
    <a role="button" class="btn btn-danger backFlipButton"><i class="fas fa-times"></i></a>
</form>`;
                        html += "</div>"; //close back face
                        html += "</div>"; //close card
                        break;
                    // case "Adeptus Custodes":
                    case 21:
                        html += `<div class="front">`;
                        html += "<img class='card-img-top factionImg' src='http://bakadesign.dk/backoffice/wp-content/uploads/2018/06/Adeptus-Custodes.svg'>";
                        html += `<i class="fas fa-pen editArmyIcon"></i>`;
                        html += `<div class="card-body">`;
                        html += `<h5 class="card-title">${army.name}</h5>`;
                        html += `<p>${army.sizeClass}</p>`;

                        if (army.notes != null) {
                            html += `<p class="card-text">${army.notes}</p>`
                        }
                        html += `<a href="/armybuilder/${id}" role="button" class="btn btn-success">Build!</a>`;
                        html += `<a href="#" role="button" class="btn btn-danger deleteArmyButton" id="deleteArmy" + ${id} onclick="deleteArmy(${id})">Delete</a>`;
                        html += `<input class="armyId" type="hidden" value=${id}>`
                        html += "</div>"; //close card body
                        html += "</div>"; //close front face
                        html += `<div class="back">`;
                        html += `<form id="editArmyForm">
    <div class="form-group">
        <input type="text" class="form-control" id="armyName${id}" name="name" placeholder="Army Name">
    </div>
    <div class="form-group">
        <select name="faction" id="factionTypeId${id}" class="form-control">
            <option selected="selected"> Faction </option>
            <option value="1">Chaos Space Marines</option>
            <option value="2">Chaos Demons</option>
            <option value="3">Drukhari</option>
            <option value="4">Craftworld Eldar</option>
            <option value="5">Eldar Harlequins</option>
            <option value="6">Necrons</option>
            <option value="7">Orks</option>
            <option value="8">T'au Empire</option>
            <option value="9">Tyranids</option>
            <option value="10">Genestealer Cults</option>
            <option value="11">Ultramarines</option>
            <option value="12">Blood Angels</option>
            <option value="13">Dark Angels</option>
            <option value="14">Grey Knights</option>
            <option value="15">Deathwatch</option>
            <option value="16">Space Wolves</option>
            <option value="17">Astra Militarum</option>
            <option value="18">Adepta Sororitas</option>
            <option value="19">Imperial Knights</option>
            <option value="20">Adeptus Mechanicus</option>
            <option value="21">Adeptus Custodes</option>
        </select>
    </div>

    <div class="form-group">
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeSmall${id}" value="small">
                <label class="form-check-label" for="armySizeSmall${id}">Small</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeMedium${id}" value="medium"
                    checked>
                <label class="form-check-label" for="armySizeMedium${id}">Medium</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sizeClass" id="armySizeLarge${id}" value="large">
                <label class="form-check-label" for="armySizeLarge${id}">Large</label>
            </div>
        </div>
    <div class="form-group">
        <input type="text" name="armyNotes" class="form-control" id="armyNotes${id}" placeholder="Notes">
    </div>
    <a role="button" id="editArmyButton${id}" class="btn btn-success editArmyButton"><i class="fas fa-check"></i></a>
    <a role="button" class="btn btn-danger backFlipButton"><i class="fas fa-times"></i></a>
</form>`;
                        html += "</div>"; //close back face
                        html += "</div>"; //close card
                        break;

                }

                $("#cardTable").append(html);

                $(".flipCard").flip({
                    trigger: 'manual',
                    reverse: true
                });

                $(".editArmyIcon").on("click", function () {
                    var card = $(this).parent().parent();
                    var armyIdOfCard = card.attr("id");
                    var currentArmy;
                    armyArray.forEach((army) => {
                        if ($(army).attr("armyId") == armyIdOfCard) {
                            currentArmy = army;
                        }
                    });

                    $(`#armyName${currentArmy.armyId}`).val(currentArmy.name);
                    $(`#factionTypeId${currentArmy.armyId}`).val(currentArmy.factionTypeId);
                    switch (currentArmy.sizeClass) {
                        case "small":
                            $(`#armySizeSmall${currentArmy.armyId}`).prop("checked", true);
                            break;
                        case "medium":
                            $(`#armySizeMedium${currentArmy.armyId}`).prop("checked", true);
                            break;
                        case "large":
                            $(`#armySizeLarge${currentArmy.armyId}`).prop("checked", true);
                            break;
                    }
                    $(`#armyNotes${currentArmy.armyId}`).val(currentArmy.notes);
                    card.flip(true);
                });

                $(".backFlipButton").on("click", function () {
                    var card = $(this).parent().parent().parent();
                    card.flip(false);
                });

                $(".editArmyButton").on("click", function (event) {
                    event.preventDefault();
                    var armyId = $(this).parent().parent().parent().attr("id");
                    $.ajax({
                        method: "PUT",
                        url: `/api/editArmy/${username}/${armyId}`,
                        data: JSON.stringify({
                            armyId: parseInt(armyId),
                            name: $(`#armyName${armyId}`).val(),
                            notes: $(`#armyNotes${armyId}`).val(),
                            sizeClass: $("input[name=sizeClass]:checked").val(),
                            username: username,
                            factionTypeId: parseInt($(`#factionTypeId${armyId}`).val())
                        }),
                        headers: {
                            "Accept": "application/json",
                            "Content-Type": "application/json"
                        },
                        success: function (editedArmy) {
                            console.log("editedArmy: " + editedArmy);
                            $("#armyEditSuccessToast").toast("show");
                            $("#armyEditSuccessToast").on("hidden.bs.toast", function () {
                                window.location.replace("/home");
                            });
                        },
                        error: function () {
                            $("#armyEditSuccessToast").toast("show");
                        }
                    });
                });
    
            });

            
        },

        error: function () {
            alert("Something went wrong!");
        }
    });
}