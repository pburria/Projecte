using MySQL;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Globalization;
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

// La plantilla de elemento Control de usuario está documentada en https://go.microsoft.com/fwlink/?LinkId=234236

namespace GestioCartaMenu.View
{
    public sealed partial class UICategories : UserControl
    {
        public UICategories()
        {
            this.InitializeComponent();
        }

        public Catagoria Catagoria
        {
            get { return (Catagoria)GetValue(CatagoriaProperty); }
            set { SetValue(CatagoriaProperty, value); }
        }

        // Using a DependencyProperty as the backing store for Catagoria.  This enables animation, styling, binding, etc...
        public static readonly DependencyProperty CatagoriaProperty =
            DependencyProperty.Register("Catagoria", typeof(Catagoria), typeof(UICategories), new PropertyMetadata(null));

        private void ucCategoria_Loaded(object sender, RoutedEventArgs e)
        {
            string color = Catagoria.Color;
            stp.Background =new SolidColorBrush(ConvertColorFromHexString(color).Color);
        }
        private SolidColorBrush ConvertColorFromHexString(string colorStr)
        {
            colorStr = colorStr.Replace("#", string.Empty);
            var r = (byte)System.Convert.ToUInt32(colorStr.Substring(0, 2), 16);
            var g = (byte)System.Convert.ToUInt32(colorStr.Substring(2, 2), 16);
            var b = (byte)System.Convert.ToUInt32(colorStr.Substring(4, 2), 16);
            SolidColorBrush myBrush = new SolidColorBrush(Windows.UI.Color.FromArgb(255, r, g, b));
            return myBrush;
        }
    }
}
