using MySQL;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;

// La plantilla de elemento Página en blanco está documentada en https://go.microsoft.com/fwlink/?LinkId=402352&clcid=0xc0a

namespace ImprimirCarta
{
    /// <summary>
    /// Página vacía que se puede usar de forma independiente o a la que se puede navegar dentro de un objeto Frame.
    /// </summary>
    public sealed partial class MainPage : Page
    {
        public MainPage()
        {
            this.InitializeComponent();
        }

        private void Page_Loaded(object sender, RoutedEventArgs e)
        {
            cmbCategories.ItemsSource = Catagoria.GetCatagorias();
        }

        private void btnImport_Click(object sender, RoutedEventArgs e)
        {
            int categoria=0;
            if (cmbCategories.SelectedValue != null)
            {
                Catagoria cat = (Catagoria)cmbCategories.SelectedValue;
                categoria = cat.Codi;
            }
            
            webView.Navigate(new Uri(@"http://51.68.224.27:8080/jasperserver/flow.html?_flowId=viewReportFlow&_flowId=viewReportFlow&ParentFolderUri=%2Fdam2-pburria%2FRestaurant&reportUnit=%2Fdam2-pburria%2FRestaurant%2FCarta&standAlone=true&j_username=dam2-pburria&j_password=47110807E&catagoria="+categoria));                      
        }
    }
}
