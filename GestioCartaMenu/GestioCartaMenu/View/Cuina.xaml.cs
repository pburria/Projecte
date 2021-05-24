using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using System.Timers;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI.Core;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;
using MySQL;
using System.Collections.ObjectModel;

// La plantilla de elemento Página en blanco está documentada en https://go.microsoft.com/fwlink/?LinkId=234238

namespace GestioCartaMenu.View
{
    /// <summary>
    /// Una página vacía que se puede usar de forma independiente o a la que se puede navegar dentro de un objeto Frame.
    /// </summary>
    public sealed partial class Cuina : Page
    {
        public Cuina()
        {
            this.InitializeComponent();
        }
        public static ObservableCollection<Comanda> comandas;
        private List<Comanda> lComanas=new List<Comanda>();
        private Timer t;
        private void Page_Loaded(object sender, RoutedEventArgs e)
        {
            mostrarLlista();
            t = new Timer(2000);
            t.Elapsed += T_Elapsed;
            t.Start();
        }

        private void mostrarLlista()
        {
            lComanas = ordenarIMostrarLlista(Comanda.getComandesNoFinalitzades());
            comandas = new ObservableCollection<Comanda>(lComanas);
            lsvComandes.ItemsSource = comandas;
        }
        private List<Comanda> ordenarIMostrarLlista(List<Comanda> llista)
        {
            llista.Sort(delegate (Comanda x, Comanda y) {
                return x.Data.CompareTo(y.Data);
            });
            return llista;
        }
        private void T_Elapsed(object sender, ElapsedEventArgs e)
        {
            try
            {
                Dispatcher.RunAsync(
                    CoreDispatcherPriority.High,
                    () =>
                    {
                        mostrarLlista();
                    });
            }
            catch (Exception)
            {
            }

        }

        private void Page_Unloaded(object sender, RoutedEventArgs e)
        {
            t.Stop();
        }
    }
}
