let daysToAdd = 0

function tipoDoPrazo() {
	let prazoCivelRadioBtn = document.getElementsByName('processual')[0]
	let tipoPrazo = prazoCivelRadioBtn.checked ? 'civel/' : 'penal/'
	return tipoPrazo
}

function sendData() {
	let startDate = document.getElementById("start-date").value
	let city = document.getElementById("city").value
	let endPoint = tipoDoPrazo() + city
	let url = 'https://prazotjsp.azurewebsites.net/' + endPoint
	// let url = 'http://localhost:8081/' + endPoint
	const requestObj = {
		startDate,
		daysToAdd
	}

	fetch(url, {
		method: 'POST',
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(requestObj)
	})
		.then(response => {
			if (!response.ok) {
				throw new Error("Erro ao receber os dados");
			}
			return response.json();
		})
		.then(data => {
			let backEndFinalDate = data.prazoFinal
			let backEndDescricao = data.descricao
			showPrazoFinal(backEndFinalDate)
			showFeriados(backEndDescricao)
		})
		.catch(error => {
			console.error("Erro:", error);
		})

}

function setDaysByRadioButton(value) {
	let outros = document.getElementsByClassName("outro")
	for (let outro of outros) {
		let shouldHide = value != null
		let hiddenClassName = "hidden"

		if (shouldHide) {
			outro.classList.add(hiddenClassName)
		} else {
			outro.classList.remove(hiddenClassName)
		}
		daysToAdd = +value
	}
}

function setDaysByOther() {
	let element = document.getElementById("prazo-value")
	daysToAdd = +element.value
}

function toggleInfo() {
	let infoButton = document.getElementsByClassName("botao-info")[0]
	if (infoButton.textContent === "Mostrar informações") {
		infoButton.textContent = "Esconder"
	} else {
		infoButton.textContent = "Mostrar informações"
	}
	let info = document.getElementsByClassName("informacoes")[0]
	info.classList.toggle("hidden")
}

function showPrazoFinal(prazoFinalValue) {
	let prazoFinal = document.getElementById("prazo-final")
	prazoFinal.classList.remove("hidden")

	let prazoFinalData = document.getElementById("prazo-final-data")
	prazoFinalData.innerHTML = prazoFinalValue
}

function showFeriados(listaFeriados) {
	let feriados = document.getElementById("feriados")
	if (listaFeriados.length != 0) {
		feriados.innerHTML = ""
		feriados.classList.remove("hidden")
		let label = document.createElement("div")
		label.className = "label"
		if (tipoDoPrazo() === 'civel/') {
			label.innerHTML = "Feriados e suspensões no período: "
		} else if (tipoDoPrazo() === 'penal/') {
			label.innerHTML = "Observações:"
		}
		feriados.appendChild(label)
		for (let feriadoDescricao of listaFeriados) {
			let feriado = document.createElement('div')
			feriado.classList.add("feriado")

			let descricao = document.createElement('div')
			descricao.classList.add("descricao")
			descricao.innerHTML = feriadoDescricao

			feriado.appendChild(descricao)
			feriados.appendChild(feriado)
		}
	} else {
		feriados.innerHTML = ""
	}

}
