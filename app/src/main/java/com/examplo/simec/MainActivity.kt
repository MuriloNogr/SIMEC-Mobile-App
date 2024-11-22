package com.examplo.simec

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.examplo.simec.data.network.SimecApi
import com.examplo.simec.data.network.UnsafeRetrofitInstance
import com.examplo.simec.data.storage.AsyncStorageHelper
import com.examplo.simec.ui.screens.*
import com.examplo.simec.ui.theme.SIMECTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val simecApi: SimecApi by lazy {
        UnsafeRetrofitInstance.createUnsafeRetrofit().create(SimecApi::class.java)
    }

    private lateinit var storageHelper: AsyncStorageHelper
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        storageHelper = AsyncStorageHelper(this)
        auth = FirebaseAuth.getInstance()

        setContent {
            SIMECTheme {
                AppNavigation(
                    simecApi = simecApi,
                    storageHelper = storageHelper,
                    auth = auth,
                    onDeleteCondominio = { id ->
                        lifecycleScope.launch {
                            try {
                                simecApi.deleteCondominio(id)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun AppNavigation(
    simecApi: SimecApi,
    storageHelper: AsyncStorageHelper,
    auth: FirebaseAuth,
    onDeleteCondominio: suspend (Long) -> Unit
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = if (auth.currentUser == null) "login" else "initial_home") {
        // Tela de Login
        composable("login") {
            LoginScreen(
                onLoginSuccess = { navController.navigate("initial_home") },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }

        // Tela de Cadastro
        composable("register") {
            RegisterScreen(
                onRegisterSuccess = { navController.navigate("login") },
                onBack = { navController.popBackStack() }
            )
        }

        // Tela Inicial (antiga)
        composable("initial_home") {
            InitialHomeScreen(onNavigateToCondominios = {
                navController.navigate("condominios")
            })
        }

        // Tela de Condomínios (CRUD + AsyncStorage)
        composable("condominios") {
            CondominiosScreen(
                api = simecApi,
                storageHelper = storageHelper,
                onDetails = { id -> navController.navigate("details/$id") },
                onAnalyze = { id -> navController.navigate("analyze/$id") },
                onCreate = { navController.navigate("create") },
                onEdit = { id -> navController.navigate("edit/$id") },
                onDelete = { id ->
                    kotlinx.coroutines.GlobalScope.launch {
                        onDeleteCondominio(id)
                    }
                }
            )
        }

        // Tela de Detalhes de Condomínio
        composable("details/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
            if (id != null) {
                DetalheCondominioScreen(
                    condominioId = id,
                    onBack = { navController.popBackStack() },
                    api = simecApi
                )
            }
        }

        // Tela de Análise de Consumo
        composable("analyze/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
            if (id != null) {
                AnalyzeConsumptionScreen(
                    condominioId = id,
                    onBack = { navController.popBackStack() },
                    api = simecApi
                )
            }
        }

        // Tela de Criação de Condomínio
        composable("create") {
            CreateCondominioScreen(
                api = simecApi,
                onBack = { navController.popBackStack() }
            )
        }

        // Tela de Edição de Condomínio
        composable("edit/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
            if (id != null) {
                EditCondominioScreen(
                    condominioId = id,
                    api = simecApi,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
