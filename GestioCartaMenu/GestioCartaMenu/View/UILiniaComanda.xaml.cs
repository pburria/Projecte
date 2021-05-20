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
using MySQL;
using Windows.UI;

// La plantilla de elemento Control de usuario está documentada en https://go.microsoft.com/fwlink/?LinkId=234236

namespace GestioCartaMenu.View
{
    public sealed partial class UILiniaComanda : UserControl
    {
        public UILiniaComanda()
        {
            this.InitializeComponent();
        }

        public LiniaComanda LiniaComanda
        {
            get { return (LiniaComanda)GetValue(LiniaComandaProperty); }
            set { SetValue(LiniaComandaProperty, value); }
        }

        // Using a DependencyProperty as the backing store for LiniaComanda.  This enables animation, styling, binding, etc...
        public static readonly DependencyProperty LiniaComandaProperty =
            DependencyProperty.Register("LiniaComanda", typeof(LiniaComanda), typeof(UILiniaComanda), new PropertyMetadata(null, MarcarFetCallbackStatic));

        private static void MarcarFetCallbackStatic(DependencyObject d, DependencyPropertyChangedEventArgs e)
        {
            UILiniaComanda ui = (UILiniaComanda)d;
            ui.MarcarFetCallback(e);
            
        }
        private void MarcarFetCallback(DependencyPropertyChangedEventArgs e)
        {
            estaFet();
            if (LiniaComanda != null)
            {
                LiniaComanda.PropertyChanged += LiniaComanda_PropertyChanged;
            }
        }
        private void LiniaComanda_PropertyChanged(object sender, System.ComponentModel.PropertyChangedEventArgs e)
        {
            estaFet();
        }

        private void estaFet()
        {
            if (LiniaComanda != null)
            {
                if (LiniaComanda.Acabat)
                {
                    stp.Background = new SolidColorBrush(Colors.Lime);
                }
            }
        }

        private void ucLiniaComanda_Loaded(object sender, RoutedEventArgs e)
        {
            if (LiniaComanda.NomPlat.Length > 22)
            {
                txbNom.Text = LiniaComanda.NomPlat.Substring(0, 22);
                txbNom1.Text= LiniaComanda.NomPlat.Substring(22);
            }
            else
            {
                txbNom.Text = LiniaComanda.NomPlat;
            }
        }
    }
}
