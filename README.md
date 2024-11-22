![LogoSimec](https://github.com/user-attachments/assets/40e08500-f909-496c-85f0-8b6e7b8ed9a4)


# 🌱 SIMEC Mobile App

**SIMEC Mobile** é um aplicativo Android desenvolvido em Kotlin e Jetpack Compose que visa o gerenciamento de condomínios e o monitoramento de consumo de energia. O aplicativo se integra com Firebase para autenticação e utiliza Retrofit para comunicação com APIs RESTful.

---

## Links


[Link para o vídeo de apresentação](https://www.youtube.com/watch?v=x0PZ2OoCa7k)

---

## 🚀 Recursos

- **Gerenciamento de Condomínios**:
  - Criação, leitura, edição e exclusão de dados de condomínios.
- **Análise de Consumo de Energia**:
  - Integração com API para fornecer análises detalhadas de consumo de energia.
- **Autenticação com Firebase**:
  - Tela de login e cadastro utilizando Firebase Authentication.
- **Persistência Local**:
  - Armazenamento de preferências usando AsyncStorage.
- **Interface Moderna**:
  - Criada com Jetpack Compose, com tema personalizado e suporte para diferentes telas.

## 🛠 Tecnologias Utilizadas

- **Linguagem**: Kotlin
- **Interface**: Jetpack Compose
- **Comunicação de Rede**: Retrofit
- **Autenticação**: Firebase Authentication
- **Persistência**: AsyncStorage (SharedPreferences)
- **Design e Navegação**: Material Design 3 e Navigation Compose

## 📂 Estrutura do Projeto

### **Pasta: `data`**
- **models**: Define a estrutura dos dados, como `Condominio`.
- **network**: Configurações e interfaces de APIs com Retrofit.

### **Pasta: `ui`**
- **screens**: Todas as telas, como `CondominiosScreen`, `CreateCondominioScreen` e `LoginScreen`.
- **theme**: Definições de cores, tipografia e estilos.

### **Pasta: `storage`**
- Classe `AsyncStorageHelper` para manipulação de dados persistentes.

## 📖 Exemplo de Fluxo

1. **Login**:
   - Usuário insere email e senha para autenticar no Firebase.
2. **Cadastro de Condomínio**:
   - Acesse a tela de condomínios e clique em "Novo".
   - Preencha os campos e clique em "Salvar".
3. **Análise de Consumo**:
   - Na tela de condomínios, clique em "Analisar Consumo" para receber dicas e insights gerados por IA.


## 📜 Diagrama UML

![SIMECMobileUML](https://github.com/user-attachments/assets/699d54da-0150-4da4-a211-8d71f93dbd46)


## 👥 Contribuidores

- **Luis Fernando Menezes Zampar** - RM 550531
- **Diogo Fagioli Bombonatti** - RM 551694
- **Murilo Nogueira** - RM 89162
- **Gabriel Galdino da Silva** - RM 550711

