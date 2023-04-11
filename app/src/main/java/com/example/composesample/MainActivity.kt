package com.example.composesample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
private fun MyApp(
    modifier: Modifier = Modifier,
    names: List<String> = listOf("World", "Compose")
) {
    Column(modifier) {
        for (name in names) {
            Greeting(name = name)
        }
    }
}

@Composable
fun Greeting(name: String) {
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
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Hello,")
                Text(text = name)
            }
            ElevatedButton(onClick = { /*TODO*/ }) {
                Text(text = "Sow more")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    ComposeSampleTheme {
        MyApp()
    }
}