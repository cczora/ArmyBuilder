//globals and passed-in values from backend
var user, factions, unitTypes, sizeClass, detachmentLimit, armyId, detachments;
var newDetachmentId = null;
var detachmentCount = 0;

$(document).ready(function () {
    user = $(username);
    armyId = $("#armyId").val();
    sizeClass = $("#armySizeClass").val();
    //set up context menus
    loadDetachmentTypes();
    loadUnitTypes();
    //set up detachment slots
    setDetachmentLimitsandSetUpDetachmentSlots(sizeClass);
    factions = $(factions);

    var detachments = $(army).prop("detachments");

    //load army details for this army and populate the divs with them
    if (detachments != null && detachments != undefined) {
        //loading screen
        // setTimeout(function () {
        loadArmyInfo(detachments);
        // }, 1000);

    }
});



function loadDetachmentTypes() {

    $.ajax({
        method: "GET",
        url: "/api/detachmentTypes",
        success: function (detachmentTypes) {

            detachmentTypes.forEach(d => {
                var name = d.name;
                var cmdPts = d.commandPoints;
                var type = d.detachmentTypeId;
                var html = `<div class="card detachmentTypeCard border border-primary" data-id="${type}" data-name="${name}" id="detachmentType${type}" onclick="addDetachment(this)">`;

                switch (name) {
                    case "Patrol":
                        html += `<div class="card-body">`;

                        html += `<p class="title1">${name}</p><p class="title2">${cmdPts} CP</p>`;

                        html += "</div>";
                        break;
                    case "Battalion":
                        html += `<div class="card-body">`;

                        html += `<p class="title1">${name}</p><p class="title2">${cmdPts} CP</p>`;

                        html += "</div>";
                        break;
                    case "Brigade":
                        html += `<div class="card-body">`;

                        html += `<p class="title1">${name}</p><p class="title2">${cmdPts} CP</p>`;

                        html += "</div>";
                        break;
                    case "Supreme Command":
                        html += `<div class="card-body">`;

                        html += `<p class="title1">${name}</p><p class="title2">${cmdPts} CP</p>`;

                        html += "</div>";
                        break;
                    case "Super-Heavy":
                        html += `<div class="card-body">`;

                        html += `<p class="title1">${name}</p><p class="title2">${cmdPts} CP</p>`;

                        html += "</div>";
                        break;
                    case "Air Wing":
                        html += `<div class="card-body">`;

                        html += `<p class="title1">${name}</p><p class="title2">${cmdPts} CP</p>`;

                        html += "</div>";
                        break;
                    case "Super-Heavy Auxiliary":
                        html += `<div class="card-body">`;

                        html += `<p class="title1">${name}</p><p class="title2">${cmdPts} CP</p>`;

                        html += "</div>";
                        break;
                    case "Fortification Network":
                        html += `<div class="card-body">`;

                        html += `<p class="title1">${name}</p><p class="title2">${cmdPts} CP</p>`;

                        html += "</div>";
                        break;
                    case "Auxiliary Support":
                        html += `<div class="card-body">`;

                        html += `<p class="title1">${name}</p><p class="title2">${cmdPts} CP</p>`;

                        html += "</div>";
                        break;
                    case "Vanguard":
                        html += `<div class="card-body">`;

                        html += `<p class="title1">${name}</p><p class="title2">${cmdPts} CP</p>`;

                        html += "</div>";
                        break;
                    case "Spearhead":
                        html += `<div class="card-body">`;

                        html += `<p class="title1">${name}</p><p class="title2">${cmdPts} CP</p>`;

                        html += "</div>";
                        break;
                    case "Outrider":
                        html += `<div class="card-body">`;

                        html += `<p class="title1">${name}</p><p class="title2">${cmdPts} CP</p>`;

                        html += "</div>";
                        break;

                }

                // html += `<input type="hidden" id="detachmentType${name}" value="${name}">`

                html += `</div>`;

                $("#detachmentTypeContainer").append(html);


            });
        },
        error: function () {
            alert("Something went wrong in getting detachment types!");
        }
    });
}

function loadUnitTypes() {

    $.ajax({
        method: "GET",
        url: "/api/unitTypes",
        success: function (unitTypes) {

            unitTypes.forEach(u => {
                var name = u.name;
                var type = u.unitTypeId;
                var html = `<div class="draggable card unitTypeCard border border-light" data-id="${type}" data-name="${name}" id="unitType${type}">`;

                switch (name) {
                    case "HQ":
                        html += `<div class="card-body">`;
                        //edit button on top right, delete X on extreme top right, main body of card is big image/icon of unit type
                        html += `<i class="fas fa-edit fa-2x"></i>`;
                        html += `<i class="fas fa-trash-alt fa-2x"></i>`;
                        html += `<i class="fas fa-skull unitTypeIcon"></i>`;

                        html += "</div>";
                        break;
                    case "Troop":
                        html += `<div class="card-body">`;
                        html += `<i class="fas fa-edit fa-2x"></i>`;
                        html += `<i class="fas fa-trash-alt fa-2x"></i>`;
                        html += `<i class="fas fa-caret-square-right unitTypeIcon"></i>`;

                        html += "</div>";
                        break;
                    case "Elite":
                        html += `<div class="card-body">`;
                        html += `<i class="fas fa-edit fa-2x"></i>`;
                        html += `<i class="fas fa-trash-alt fa-2x"></i>`;
                        html += `<i class="fas fa-skull-crossbones unitTypeIcon"></i>`;


                        html += "</div>";
                        break;
                    case "Fast Attack":
                        html += `<div class="card-body">`;
                        html += `<i class="fas fa-edit fa-2x"></i>`;
                        html += `<i class="fas fa-trash-alt fa-2x"></i>`;
                        html += `<i class="fas fa-bolt unitTypeIcon"></i>`;

                        html += "</div>";
                        break;
                    case "Heavy Support":
                        html += `<div class="card-body">`;
                        html += `<i class="fas fa-edit fa-2x"></i>`;
                        html += `<i class="fas fa-trash-alt fa-2x"></i>`;
                        html += `<i class="fas fa-spa unitTypeIcon"></i>`;

                        html += "</div>";
                        break;
                    case "Dedicated Transport":
                        html += `<div class="card-body">`;
                        html += `<i class="fas fa-edit fa-2x"></i>`;
                        html += `<i class="fas fa-trash-alt fa-2x"></i>`;
                        html += `<i class="fas fa-arrow-alt-circle-up unitTypeIcon"></i>`;

                        html += "</div>";
                        break;
                    case "Flyer":
                        html += `<div class="card-body">`;
                        html += `<i class="fas fa-edit fa-2x"></i>`;
                        html += `<i class="fas fa-trash-alt fa-2x"></i>`;
                        html += `<i class="fas fa-feather-alt unitTypeIcon"></i>`;

                        html += "</div>";
                        break;
                    case "Fortification":
                        html += `<div class="card-body">`;
                        html += `<i class="fas fa-edit fa-2x"></i>`;
                        html += `<i class="fas fa-trash-alt fa-2x"></i>`;
                        html += `<i class="fas fa-kaaba unitTypeIcon"></i>`;

                        html += "</div>";
                        break;
                    case "Lord of War":
                        html += `<div class="card-body">`;
                        html += `<i class="fas fa-edit fa-2x"></i>`;
                        html += `<i class="fas fa-trash-alt fa-2x"></i>`;
                        html += `<i class="fas fa-fist-raised unitTypeIcon"></i>`;
                        html += "</div>";
                        break;

                }

                html += `</div>`;

                $("#draggableContainer").append(html);

                $(".draggable").draggable({
                    opacity: 0.7,
                    helper: 'clone',
                    containment: "body",
                    scroll: false,
                    cursor: "move"
                });

                $(".draggable").on("hover", function() {
                    $(this).css("cursor: grab");
                });

                $(".draggable").on("mousedown", function() {
                    $(this).css("cursor: grabbing");
                })

                $(".draggable").on("dragstop", function(event, ui) {
                    $(this).css("cursor: grab");
                });

            });
    },
    error: function() {
        alert("Something went wrong with loading unit types!");
    }
});
}

function setDetachmentLimitsandSetUpDetachmentSlots(sizeClass) {
    //set the limit for detachments
    switch (sizeClass) {
        case "small":
            detachmentLimit = 2;
            break;
        case "medium":
            detachmentLimit = 3;
            break;
        case "large":
            detachmentLimit = 4;
            break;
    }

    //make divs
    for (let i = 0; i < detachmentLimit; i++) {
        var html = `<div class="detachmentModule d-flex justify-content-around flex-wrap" id="detachment${i}" data-id="${i}"></div>`;
        $("#mainBuilderWindow").append(html);
    }
    //style detachment slots appropriately in the main window
    switch (detachmentLimit) {
        case 2:
            $("#mainBuilderWindow").addClass("small");
            $(".detachmentModule").css("width: 80%", "height: 50%", "margin: 0 auto");
            $("#detachment0").addClass("row");
            $("#detachment1").addClass("row");
            break;
        case 3:
            $("#mainBuilderWindow").addClass("medium");
            $(".detachmentModule").css("width: 80%", "height: 30%", "margin: 0 auto");
            $("#detachment0").addClass("row");
            $("#detachment1").addClass("row");
            $("#detachment2").addClass("row");
            break;
        case 4:
            $("#mainBuilderWindow").addClass("large");
            break;
    }
}

function loadArmyInfo(detachments) {
    var detachmentModule = 0;
    for (let i = 0; i < detachments.length; i++) {
        var detachment = $(detachments[i]);
        var type = detachments[i].detachmentTypeId;
        var name = detachments[i].name;
        var id = detachments[i].detachmentId;
        var html = "";

        html += `<h6 class="detachmentName" id="detachment${detachmentModule}">${name}</h6>`
        //visually set detachment slots in detachment module based on type
        switch (type) {
            case 1: //patrol req: *1-2hq*, *1-3troop*, 0-2elite, 0-2FA, 0-2HS, 0-2fly
                var requiredSlots = 2;
                for (let i = 0; i < requiredSlots; i++) {
                    if (i == 0) {
                        html += `<div class="droppable align-self-center" id="slot${i}" data-id="${i}" data-type="${type}" data-locked="false" data-unitTypeId=1 >HQ</div>`;
                    }
                    else {
                        html += `<div class="droppable align-self-center" id="slot${i}" data-id="${i}" data-type="${type}" data-locked="false" data-unitTypeId=2>Troop</div>`;
                    }
                }
                html += `<i class="fas fa-plus-circle fa-3x" id="expandDetachmentButton"></i></div><i class="fas fa-trash-alt fa-3x" id="deleteDetachmentButton" onclick="deleteDetachment(this, ${id}, ${armyId})"></i>`;
                $(`#detachment${detachmentModule}`).append(html);
                break;
            case 2: //battalion req: *2-3hq*, *3-6troop*, 0-6elite, 0-3FA, 0-3HS, 0-2fly
                var requiredSlots = 5;
                for (let i = 0; i < requiredSlots; i++) {
                    if (i < 2) {
                        html += `<div class="droppable align-self-center" id="slot${i}" data-id="${i}" data-type="${type}" data-locked="false" data-unitTypeId=1>HQ</div>`;
                    }
                    else {
                        html += `<div class="droppable align-self-center" id="slot${i}" data-id="${i}" data-type="${type}" data-locked="false" data-unitTypeId=2>Troop</div>`;
                    }
                }
                html += `<i class="fas fa-plus-circle fa-3x" id="expandDetachmentButton"></i></div><i class="fas fa-trash-alt fa-3x" id="deleteDetachmentButton" onclick="deleteDetachment(this, ${id}, ${armyId})"></i>`;
                $(`#detachment${detachmentModule}`).append(html);
                break;
            case 3: //brigade req: *3-5hq*, *6-12troop*, *3-8elite*, *3-5FA*, *3-5HS*, 0-2fly
                var requiredSlots = 18;
                for (let i = 0; i < requiredSlots; i++) {
                    if (i < 3) {
                        html += `<div class="droppable align-self-center" id="slot${i}" data-id="${i}" data-type="${type}" data-locked="false" data-unitTypeId=1>HQ</div>`;
                    }
                    else if (i >= 3 && i <= 9) {
                        html += `<div class="droppable align-self-center" id="slot${i}" data-id="${i}" data-type="${type}" data-locked="false" data-unitTypeId=2>Troop</div>`;
                    }
                    else if (i >= 10 && i <= 12) {
                        html += `<div class="droppable align-self-center" id="slot${i}" data-id="${i}" data-type="${type}" data-locked="false" data-unitTypeId=3>Elite</div>`;
                    }
                    else if (i >= 13 && i <= 15) {
                        html += `<div class="droppable align-self-center" id="slot${i}" data-id="${i}" data-type="${type}" data-locked="false" data-unitTypeId=4>Fast Attack</div>`;
                    }
                    else {
                        html += `<div class="droppable align-self-center" id="slot${i}" data-id="${i}" data-type="${type}" data-locked="false" data-unitTypeId=5>Heavy Support</div>`;
                    }
                }
                html += `<i class="fas fa-plus-circle fa-3x" id="expandDetachmentButton"></i></div><i class="fas fa-trash-alt fa-3x" id="deleteDetachmentButton" onclick="deleteDetachment(this, ${id}, ${armyId})"></i>`;
                $(`#detachment${detachmentModule}`).append(html);
                break;
            case 4: //supreme command req: *3-5hq*, 0-1elite, 0-1LOW
                var requiredSlots = 3;
                for (let i = 0; i < requiredSlots; i++) {
                    html += `<div class="droppable align-self-center" id="slot${i}" data-id="${i}" data-type="${type}" data-locked="false" data-unitTypeId=1>HQ</div>`;
                }
                html += `<i class="fas fa-plus-circle fa-3x" id="expandDetachmentButton"></i></div><i class="fas fa-trash-alt fa-3x" id="deleteDetachmentButton" onclick="deleteDetachment(this, ${id}, ${armyId})"></i>`;
                $(`#detachment${detachmentModule}`).append(html);
                break;
            case 5: //super-heavy  *3-5LOW*
                var requiredSlots = 3;
                for (let i = 0; i < requiredSlots; i++) {
                    html += `<div class="droppable align-self-center" id="slot${i}" data-id="${i}" data-type="${type}" data-locked="false" data-unitTypeId=9>Lord of War</div>`;
                }
                html += `<i class="fas fa-plus-circle fa-3x" id="expandDetachmentButton"></i></div><i class="fas fa-trash-alt fa-3x" id="deleteDetachmentButton" onclick="deleteDetachment(this, ${id}, ${armyId})"></i>`;
                $(`#detachment${detachmentModule}`).append(html);
                break;
            case 6: //air wing  *3-5fly*
                var requiredSlots = 3;
                for (let i = 0; i < requiredSlots; i++) {
                    html += `<div class="droppable align-self-center" id="slot${i}" data-id="${i}" data-type="${type}" data-locked="false" data-unitTypeId=7>Flyer</div>`;
                }
                html += `<i class="fas fa-plus-circle fa-3x" id="expandDetachmentButton"></i></div><i class="fas fa-trash-alt fa-3x" id="deleteDetachmentButton" onclick="deleteDetachment(this, ${id}, ${armyId})"></i>`;
                $(`#detachment${detachmentModule}`).append(html);
                break;
            case 7: //super-heavy auxiliary *1LOW*
                html += `<div class="droppable align-self-center" id="slot${i}" data-id="${i}" data-type="${type}" data-locked="false" data-unitTypeId=9>Lord of War</div>`;
                html += `<i class="fas fa-plus-circle fa-3x" id="expandDetachmentButton"></i></div><i class="fas fa-trash-alt fa-3x" id="deleteDetachmentButton" onclick="deleteDetachment(this, ${id}, ${armyId})"></i>`;
                $(`#detachment${detachmentModule}`).append(html);
                break;
            case 8: //fortification network *1-3fort*
                html += `<div class="droppable align-self-center" id="slot${i}" data-id="${i}" data-type="${type}" data-locked="false" data-unitTypeId=8>Fortification</div>`;
                html += `<i class="fas fa-plus-circle fa-3x" id="expandDetachmentButton"></i></div><i class="fas fa-trash-alt fa-3x" id="deleteDetachmentButton" onclick="deleteDetachment(this, ${id}, ${armyId})"></i>`;
                $(`#detachment${detachmentModule}`).append(html);
                break;
            case 9: //auxiliary support 1 of any type (-1 cp for each)
                html += `<div class="droppable align-self-center" id="slot${i}" data-id="${i}" data-type="${type}" data-locked="false" data-unitTypeId=0>Any</div>`;
                html += `<i class="fas fa-plus-circle fa-3x" id="expandDetachmentButton"></i></div><i class="fas fa-trash-alt fa-3x" id="deleteDetachmentButton" onclick="deleteDetachment(this, ${id}, ${armyId})"></i>`;
                $(`#detachment${detachmentModule}`).append(html);
                break;
            case 10: //vanguard *1-2hq*, 0-3troop, *3-6elite*, 0-2FA, 0-2HS, 0-2fly
                var requiredSlots = 4;
                for (let i = 0; i < requiredSlots; i++) {
                    if (i == 0) {
                        html += `<div class="droppable align-self-center" id="slot${i}" data-id="${i}" data-type="${type}" data-locked="false" data-unitTypeId=1>HQ</div>`;
                    }
                    else {
                        html += `<div class="droppable align-self-center" id="slot${i}" data-id="${i}" data-type="${type}" data-locked="false" data-unitTypeId=3>Elite</div>`;
                    }
                }
                html += `<i class="fas fa-plus-circle fa-3x" id="expandDetachmentButton"></i></div><i class="fas fa-trash-alt fa-3x" id="deleteDetachmentButton" onclick="deleteDetachment(this, ${id}, ${armyId})"></i>`;
                $(`#detachment${detachmentModule}`).append(html);
                break;
            case 11: //speadhead *1-2hq*, 0-3troop, 0-2elite, 0-2FA, *3-6HS*, 0-2fly
                var requiredSlots = 4;
                for (let i = 0; i < requiredSlots; i++) {
                    if (i == 1) {
                        html += `<div class="droppable align-self-center" id="slot${i}" data-id="${i}" data-type="${type}" data-locked="false" data-unitTypeId=1>HQ</div>`;
                    }
                    else {
                        html += `<div class="droppable align-self-center" id="slot${i}" data-id="${i}" data-type="${type}" data-locked="false" data-unitTypeId=5>Heavy Support</div>`;
                    }
                }
                html += `<i class="fas fa-plus-circle fa-3x" id="expandDetachmentButton"></i></div><i class="fas fa-trash-alt fa-3x" id="deleteDetachmentButton" onclick="deleteDetachment(this, ${id}, ${armyId})"></i>`;
                $(`#detachment${detachmentModule}`).append(html);
                break;
            case 12: //outrider *1-2hq*, 0-3troop, 0-2elite, *3-6FA*, 0-2HS, 0-2fly
                var requiredSlots = 4;
                for (let i = 0; i < requiredSlots; i++) {
                    if (i == 1) {
                        html += `<div class="droppable align-self-center" id="slot${i}" data-id="${i}" data-type="${type}" data-locked="false" data-unitTypeId=1>HQ</div>`;
                    }
                    else {
                        html += `<div class="droppable align-self-center" id="slot${i}" data-id="${i}" data-type="${type}" data-locked="false" data-unitTypeId=4>Fast Attack</div>`;
                    }
                }
                html += `<i class="fas fa-plus-circle fa-3x" id="expandDetachmentButton"></i></div><i class="fas fa-trash-alt fa-3x" id="deleteDetachmentButton" onclick="deleteDetachment(this, ${id}, ${armyId})"></i>`;
                $(`#detachment${detachmentModule}`).append(html);
                break;
        }
        $(".droppable").droppable({
            over: function (event, ui) {
                var unitTypeOfDraggable = $(ui.draggable).attr("data-id");
                var unitTypeOfDroppable = $(this).attr("data-unitTypeId");
                var droppableElement = $(event.target);
                if (unitTypeOfDraggable == unitTypeOfDroppable) { //the right unit type is hovering over this slot!
                    droppableElement.css("border", "solid 5px green");
                    //ui.draggable("disable");
                }
                else {
                    droppableElement.css("border", "solid 5px red");
                }
            },
            out: function (event, ui) {
                var droppableElement = $(event.target);
                droppableElement.css("border", "");
            },
            drop: function (event, ui) {
                var unitTypeOfDraggable = $(ui.draggable).attr("data-id");
                var unitTypeOfDroppable = $(this).attr("data-unitTypeId");
                var droppableElement = $(event.target);
                var draggableElement = $(ui.draggable);
                var parentDetachmentIdInDB = droppableElement.parent().attr("detachmentIdInDB");
                if (unitTypeOfDraggable === unitTypeOfDroppable) { //dropped the right unit type onto this slot!
                    draggableElement.draggable("disable");
                    //make the ajax call to send the information to the database!!
                    $.ajax({
                        method: "POST",
                        url: `/api/addUnit/${armyId}`,
                        headers: {
                            "Accept": "application/json",
                            "Content-Type": "application/json"
                        },
                        data: JSON.stringify({
                            detachmentId: id,
                            unitTypeId: parseInt(unitTypeOfDraggable)
                        }),
                        success: function(unit) {
                            $("#unitAddSuccessToast").toast("show");
                            $("#unitAddSuccessToast").on("hidden.bs.toast", function () {
                                window.location.replace(`/armybuilder/${armyId}`);
                            });
                        },
                        error: function() {
                            $("unitAddErrorToast").toast("show");
                        }
                    });
                }
                else {
                    $("#unitAddErrorToast").toast("show");
                    $("#unitAddErrorToast").on("hidden.bs.toast", function () {
                        window.location.replace(`/armybuilder/${armyId}`);
                    });
                }
            }
        });
        //another for loop here to load unit info into the detachment
        var detachmentUnits = detachment.attr("units");
        var currentSlot = 0;
        for(let j = 0; j < detachmentUnits.length; j++) {
            var slotDiv = $(`#slot${currentSlot}`);
            var unitTypeOfSlot = slotDiv.prop("outerText");
            var currentUnit = $(detachmentUnits[j]);
            var unitTypeOfCurrentUnit = $(currentUnit.prop("unitType"));
            var unitTypeNameOfCurrentUnit = unitTypeOfCurrentUnit.prop("name");
            if(currentUnit != null && (unitTypeOfSlot == unitTypeNameOfCurrentUnit)) {
                var name = currentUnit.prop("name");
                var unitType = $(currentUnit.prop("unitType"));
                var unitTypeId = currentUnit.prop("unitTypeId");
                var unitTypeName = unitType.prop("name");
                var unitIdInDB = currentUnit.prop("unitId");
                var detachmentId = currentUnit.prop("detachmentId");
                var html = `<div class="card unitTypeCard border border-light align-self-center" data-id="${type}" data-name="${name}" id="unitType${unitTypeName}">`;

                switch (unitTypeId) {
                    case 1:
                        html += `<div class="card-body detachmentUnitDocked align-self-center">`;
                        //edit button on top right, delete X on extreme top right, main body of card is big image/icon of unit type
                        html += `<i class="fas fa-edit fa-2x"></i>`;
                        html += `<i class="fas fa-trash-alt fa-2x" onclick="deleteUnit(${unitIdInDB})"></i>`;
                        html += `<i class="fas fa-skull unitTypeIcon"></i>`;

                        html += "</div>";
                        break;
                    case 2:
                        html += `<div class="card-body detachmentUnitDocked">`;
                        html += `<i class="fas fa-edit fa-2x"></i>`;
                        html += `<i class="fas fa-trash-alt fa-2x" onclick="deleteUnit(${unitIdInDB})"></i>`;
                        html += `<i class="fas fa-caret-square-right unitTypeIcon"></i>`;

                        html += "</div>";
                        break;
                    case 3:
                        html += `<div class="card-body detachmentUnitDocked">`;
                        html += `<i class="fas fa-edit fa-2x"></i>`;
                        html += `<i class="fas fa-trash-alt fa-2x" onclick="deleteUnit(${unitIdInDB})"></i>`;
                        html += `<i class="fas fa-skull-crossbones unitTypeIcon"></i>`;


                        html += "</div>";
                        break;
                    case 4:
                        html += `<div class="card-body detachmentUnitDocked">`;
                        html += `<i class="fas fa-edit fa-2x"></i>`;
                        html += `<i class="fas fa-trash-alt fa-2x" onclick="deleteUnit(${unitIdInDB})"></i>`;
                        html += `<i class="fas fa-bolt unitTypeIcon"></i>`;

                        html += "</div>";
                        break;
                    case 5:
                        html += `<div class="card-body detachmentUnitDocked">`;
                        html += `<i class="fas fa-edit fa-2x"></i>`;
                        html += `<i class="fas fa-trash-alt fa-2x" onclick="deleteUnit(${unitIdInDB})"></i>`;
                        html += `<i class="fas fa-spa unitTypeIcon"></i>`;

                        html += "</div>";
                        break;
                    case 6:
                        html += `<div class="card-body detachmentUnitDocked">`;
                        html += `<i class="fas fa-edit fa-2x"></i>`;
                        html += `<i class="fas fa-trash-alt fa-2x" onclick="deleteUnit(${unitIdInDB})"></i>`;
                        html += `<i class="fas fa-arrow-alt-circle-up unitTypeIcon"></i>`;

                        html += "</div>";
                        break;
                    case 7:
                        html += `<div class="card-body detachmentUnitDocked">`;
                        html += `<i class="fas fa-edit fa-2x"></i>`;
                        html += `<i class="fas fa-trash-alt fa-2x" onclick="deleteUnit(${unitIdInDB})"></i>`;
                        html += `<i class="fas fa-feather-alt unitTypeIcon"></i>`;

                        html += "</div>";
                        break;
                    case 8:
                        html += `<div class="card-body detachmentUnitDocked">`;
                        html += `<i class="fas fa-edit fa-2x"></i>`;
                        html += `<i class="fas fa-trash-alt fa-2x" onclick="deleteUnit(${unitIdInDB})"></i>`;
                        html += `<i class="fas fa-kaaba unitTypeIcon"></i>`;

                        html += "</div>";
                        break;
                    case 9:
                        html += `<div class="card-body detachmentUnitDocked">`;
                        html += `<i class="fas fa-edit fa-2x"></i>`;
                        html += `<i class="fas fa-trash-alt fa-2x" onclick="deleteUnit(${unitIdInDB})"></i>`;
                        html += `<i class="fas fa-fist-raised unitTypeIcon"></i>`;
                        html += "</div>";
                        break;

                }

                html += `</div>`;
                var unitTypeIdOfSlot = parseInt(slotDiv.attr("data-unitTypeId"));
                if(unitTypeOfSlot == unitTypeName) {
                    slotDiv.replaceWith(html);
                }
                currentSlot++;
            }
        }
        detachmentModule++;
    }
}

function addDetachment(object) {

    //var parentId = object.parent().attr("id");
    detachmentCount++;
    if (detachmentCount <= detachmentLimit) {
        var type = object.getAttribute("data-id");
        //add detachment to database
        $.ajax({
            type: "POST",
            url: `/api/addDetachment/${armyId}`,
            data: JSON.stringify({
                armyId: parseInt(armyId),
                detachmentTypeId: parseInt(type)
            }),
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            success: function (detachment) {
                //drop down a toast to say the add worked!
                $("#detachmentAddSuccessToast").toast("show");

                $("#detachmentAddSuccessToast").on("hidden.bs.toast", function () {
                    window.location.replace(`/armybuilder/${armyId}`);
                });
            },
            error: function () {
                $("#detachmentAddErrorToast").toast("show");
            }
        });

    }

    if (detachmentCount > detachmentLimit) {
        $("#detachmentAddFullToast").toast("show");
        return;
    }
}

function deleteDetachment(object, detachmentId, armyId) {
    $.ajax({
        method: "DELETE",
        url: `/api/deleteDetachment/${armyId}/${detachmentId}`,
        success: function () {
            $(object).parent().empty();
            if ($(object).siblings().length == 0) {
                $("#detachmentDeleteSuccessToast").toast("show");
                detachmentCount--;
                $("#detachmentDeleteSuccessToast").on("hidden.bs.toast", function () {
                    window.location.replace(`/armybuilder/${armyId}`);
                });
            }
        },
        error: function () {
            $("#detachmentDeleteErrorToast").toast("show");
        }
    });

}

function deleteUnit(unitId) {
    $.ajax({
        method: "DELETE",
        url: `/api/deleteUnit/${unitId}`,
        success: function() {
            $("unitDeleteSuccessToast").toast("show");
            $("unitDeleteSuccessToast").on("hidden.bs.toast", function() {
                window.location.replace(`/armybuilder/${armyId}`);
            });
        },
        error: function() {
            $("unitDeleteErrorToast").toast("show");
        }
    });
}