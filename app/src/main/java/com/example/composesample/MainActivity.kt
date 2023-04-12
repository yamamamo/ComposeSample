package com.example.composesample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composesample.ui.theme.ComposeSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //setContent 를 사용하여 레이아웃을 정의하지만
            //기존 뷰 시스템에서 하던 것처럼 xml파일을 사용하는 대신 이 함수에서
            //구성가능한 함수를 호출합니다.
            ComposeSampleTheme {
                MyApp()
            }
        }
    }
}

//컴포저블 재사용
@Composable
private fun MyApp(modifier: Modifier = Modifier)
{
//    var shouldShowOnboarding by remember { mutableStateOf(true)}
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true)}

    Surface(modifier) {
        if(shouldShowOnboarding){
            OnBoardingScreen(onContinueClicked = { shouldShowOnboarding = false})
            //상태가 아닌 함수를 OnBoardingScreen 에 전달하는 방식.
            // 이 컴포저블의 재사용 가능성을 높이고 다른 컴포저블이 상태를 변경하지 않도록 보호
        }else{
            Greetings()
        }
    }
}

@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
//    names: List<String> = listOf("World", "Compose")
    names: List<String> = List(1000){"$it"}
){
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) {name ->
            Greeting(name = name)
        }
    }
}

@Composable
fun Greeting(name: String) {
    //리컴포지션 - 컴포즈앱은 구성 가능한 함수를 호출하여 데이터를 ui로 변환합니다.
    //데이터가 변경되면 컴포즈는 새 데이터로 이러한 함수를 다시 실행하여 업데이트된 ui를 만듭니다.
    //remember 는 리컴포지션을 방지하는 데 사용되므로 상태가 재설정되지 않습니다.
    val expanded = remember{ mutableStateOf(false)}

    val extraPadding by animateDpAsState(
        if(expanded.value) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        //Surface, MaterialTheme 는 Material Design 과 관련된 개념이며,
        //Material Design 은 사용자 인터페이스와 환경을 만드는 데 도움을 주기 위해
        //구글 에서 만든 디자인 시스템
//        Text(text = "Hello $name!", modifier = Modifier.padding(24.dp))
        //Modifier 수정자. 수정자는 상위 요소 레이아웃 내에서 UI 요소가
        //배치되고 표시되고 동작하는 방식을 UI요소에 알려줍니다.

        Row(modifier = Modifier.padding(24.dp)){
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding.coerceAtLeast(5.dp))
            ) {
                Text(text = "Hello,")
                Text(text = name)
            }
            ElevatedButton(
                onClick = { expanded.value = !expanded.value  }
            ) {
                Text(if(expanded.value) "하이하이" else "아니하이")
            }
        }
    }
}

@Composable
fun  OnBoardingScreen(
    modifier: Modifier = Modifier,
    onContinueClicked: () -> Unit
){
//    var shouldShowOnboarding by remember { mutableStateOf(true)}
    //= 대신 by 키워드를 사용. by 는 매번 .value를 입력할 필요가 없도록 해주는 속성 위임
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment =  Alignment.CenterHorizontally
    ) {
        Text(text = "반갑다는 기초 코드랩")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("께속")
       }
    }
}


@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    ComposeSampleTheme(){
        OnBoardingScreen(onContinueClicked = {})
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    ComposeSampleTheme {
        Greetings()
    }
}

@Preview
@Composable
fun MyAppPreview() {
    ComposeSampleTheme() {
        MyApp(Modifier.fillMaxSize())
    }
}