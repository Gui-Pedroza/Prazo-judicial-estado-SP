var diasParaAdicionar = 0

function sendData() {
	// Recebe os dados do usuário
	var startDate = document.getElementById("start-date").value
	var daysToAdd = diasParaAdicionar
	var city = document.getElementById("city").value
	var prazoCivelRadioBtn = document.getElementsByName('processual')[0]
	var tipoPrazo = prazoCivelRadioBtn.checked ? 'civel/' : 'penal/'
	// valida input da quantidade de prazos
	if (startDate == "") {
		alert("Por favor, digite uma data válida")
	} else if (daysToAdd === 0) {
		alert("Por favor, selecione o prazo")
	} else {
		// Envia os dados usando AJAX		
		var xhr = new XMLHttpRequest();
		xhr.open("POST", tipoPrazo + city, true);
		xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")
		xhr.onreadystatechange = function () {
			if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
				// Atualiza a pagina com o resultado			
				var backEndStringDate = JSON.parse(xhr.responseText).prazoFinal
				var descricaoList = Array.from(JSON.parse(xhr.responseText).descricao)
				showPrazoFinal(backEndStringDate)
				showFeriados(descricaoList)
			}
		};
		xhr.send("startDate=" + startDate + "&daysToAdd=" + daysToAdd)
	}
}

function setDaysByRadioButton(value) {
	var outros = document.getElementsByClassName("outro")
	for (var outro of outros) {
		var shouldHide = value != null
		var hiddenClassName = "hidden"

		if (shouldHide) {
			outro.classList.add(hiddenClassName)
		} else {
			outro.classList.remove(hiddenClassName)
		}
		diasParaAdicionar = +value
	}
}

function setDaysByOther() {
	var element = document.getElementById("prazo-value")
	diasParaAdicionar = +element.value
}

function toggleInfo() {
	var infoButton = document.getElementsByClassName("botao-info")[0]
	if (infoButton.textContent === "Mostrar informações") {
		infoButton.textContent = "Esconder"
	} else {
		infoButton.textContent = "Mostrar informações"
	}
	var info = document.getElementsByClassName("informacoes")[0]
	info.classList.toggle("hidden")
}

function showPrazoFinal(prazoFinalValue) {
	var prazoFinal = document.getElementById("prazo-final")
	prazoFinal.classList.remove("hidden")

	var prazoFinalData = document.getElementById("prazo-final-data")
	prazoFinalData.innerHTML = prazoFinalValue
}

function showFeriados(listaFeriados) {
	var feriados = document.getElementById("feriados")
	if (listaFeriados.length != 0) {
		feriados.innerHTML = ""
		feriados.classList.remove("hidden")
		var label = document.createElement("div")
		label.className = "label"
		label.innerHTML = "Feriados e suspensões no período: "
		feriados.appendChild(label)
		for (var feriadoDescricao of listaFeriados) {
			var feriado = document.createElement('div')
			feriado.classList.add("feriado")

			var descricao = document.createElement('div')
			descricao.classList.add("descricao")
			descricao.innerHTML = feriadoDescricao

			feriado.appendChild(descricao)
			feriados.appendChild(feriado)
		}
	} else {
		feriados.innerHTML = ""
	}

}
