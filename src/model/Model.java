package model;

import com.pokegoapi.api.PokemonGo;
import com.pokegoapi.auth.GoogleAutoCredentialProvider;
import com.pokegoapi.exceptions.LoginFailedException;
import com.pokegoapi.exceptions.RemoteServerException;
import com.pokegoapi.util.SystemTimeImpl;
import com.pokegoapi.util.Time;

import okhttp3.OkHttpClient;

public class Model {

  private OkHttpClient httpClient;
  private GoogleAutoCredentialProvider login;
  private PokemonGo api;

  public Model(final String username, final String password) throws LoginFailedException, RemoteServerException {
    // TODO: Find generic way to repeat function if exception was thrown
    httpClient = new OkHttpClient();
    Time time = new SystemTimeImpl();
    login = new GoogleAutoCredentialProvider(httpClient, username, password,
        time);
    int tries = 0;
    boolean retry = false;
    do {
      try {
        retry = false;
        tries++;
        api = new PokemonGo(login, httpClient, time);
      } catch (LoginFailedException | RemoteServerException e) {
        retry = true;
        e.printStackTrace();
      }
    } while(tries <= 5 && retry);
  }
  
  /**
   * 
   */
  public PokemonGo getApi() { return api; }
}
