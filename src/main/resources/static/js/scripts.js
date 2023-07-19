function showDaysToAddInput() {
	var dropdown = document.getElementById("days-to-add-dropdown");
	var selectedValue = dropdown.options[dropdown.selectedIndex].value;
	var outroQuantidade = document.getElementById("outro-quantidade");

	if (selectedValue === "outro") {
		outroQuantidade.style.display = "block";
	} else {
		outroQuantidade.style.display = "none";
	}
}
function sendData() {
	fetch
	// Recebe os dados do usuário
	var startDate = document.getElementById("start-date").value;
	var ano = new Date(startDate).getFullYear().toString()	
	var daysToAddDropdown = document.getElementById("days-to-add-dropdown");
	var daysToAddCustom = document.getElementById("days-to-add-custom");
	var daysToAdd = daysToAddDropdown.value;

	if (daysToAdd === "outro") {
		daysToAdd = daysToAddCustom.value;
	}

	var city = document.getElementById("city").value;

	if (city === "") {
		alert("Por favor, selecione uma cidade.");
		return;
	}

	// Envia os dados usando AJAX
	var xhr = new XMLHttpRequest();
	xhr.open("POST", "/" + city, true);
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhr.onreadystatechange = function() {
		if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
			// Atualiza a pagina com o resultado
			var resultElement = document.getElementById("result");
			var resultText = "O seu prazo final é: " + new Date(xhr.responseText)
				.toLocaleDateString("pt-BR", { day: "numeric", month: "long", year: "numeric" });
			resultElement.innerHTML= resultText
			console.log(xhr.responseText)
		}
	};
	xhr.send("startDate=" + startDate + "&daysToAdd=" + daysToAdd);
}