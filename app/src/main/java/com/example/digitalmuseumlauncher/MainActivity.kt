package com.example.digitalmuseumlauncher

import android.graphics.Bitmap
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.example.digitalmuseumlauncher.ui.theme.DigitalMuseumLauncherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DigitalMuseumLauncherTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    DigitalMuseumViewer("https://friyanto.vercel.app")
                }
            }
        }
    }
}

@Composable
fun DigitalMuseumViewer(url: String, modifier: Modifier = Modifier) {
    var backEnable = false
    var webView: WebView? = null

    AndroidView(modifier = modifier, factory = { context ->
        WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)

                    backEnable = view!!.canGoBack()
                }
            }

            settings.javaScriptEnabled = true

            loadUrl(url)

            webView = this
        }
    }, update = {
        it.loadUrl(url)
        webView = it
    })
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Home"
)
@Composable
fun DigitalMuseumViewerPreview() {
    DigitalMuseumLauncherTheme {
        DigitalMuseumViewer("https://friyanto.vercel.app")
    }
}