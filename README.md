# 🛫 Sistema de Reservas de Voos

[![Java](https://img.shields.io/badge/Java-24-red.svg)](https://www.oracle.com/java/technologies/downloads/#java17)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)
[![UNIFIL](https://img.shields.io/badge/UNIFIL-Londrina-blue.svg)](https://www.unifil.br)

> Projeto desenvolvido para a disciplina de Linguagem de Programação Orientada a Objetos da UNIFIL - Centro Universitário Filadélfia de Londrina.

## 📋 Sobre

Sistema de reservas de voos desenvolvido em Java, demonstrando a aplicação prática de conceitos de POO e padrões de projeto. O projeto foi desenvolvido como parte da avaliação da disciplina de Linguagem de Programação Orientada a Objetos.

> **Nota**: Por ser um projeto acadêmico, não foi implementada persistência em banco de dados. Os dados são mantidos apenas em memória durante a execução do programa.

### ✨ Características

- Interface CLI interativa
- Gerenciamento de voos e reservas
- Validações de dados
- Padrões de projeto (Command, Singleton)
- Código documentado
- Persistência em memória (dados são perdidos ao encerrar o programa)

## 🛠️ Requisitos

- Java ^24
- Bash
- Terminal com suporte a Unicode

## 📁 Estrutura

```
src/
├── api/        # Interfaces
├── model/      # Entidades
├── repository/ # Persistência em memória
├── service/    # Regras de negócio
├── command/    # Padrão Command
├── util/       # Utilitários
└── Main.java   # Entrada
```

## 🚀 Execução

```bash
# Clone
git clone https://github.com/dev-brunoreis/flight-booking.git
cd flight-booking

# Execute
chmod +x run.sh
./run.sh
```

## 📄 Licença

MIT License - Veja [LICENSE](LICENSE) para detalhes.

---

<div align="center">
  <sub>Desenvolvido para UNIFIL - Centro Universitário Filadélfia de Londrina</sub>
</div>
